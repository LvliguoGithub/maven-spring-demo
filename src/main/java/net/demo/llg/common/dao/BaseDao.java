/**
 * 
 */
package net.demo.llg.common.dao;

import java.io.Serializable;
import java.util.List;

import net.demo.llg.common.page.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;


/**
 * 
 * history: 
 */
@Component("baseDao")
public abstract class BaseDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	protected HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	protected SessionFactory getSessionFactory() {
		return hibernateTemplate.getSessionFactory();
	}

	protected Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected Page getPage(int start, int limit, DetachedCriteria detachedCriteria) {
		start = Math.max(0, start);
		limit = Math.max(0, limit);
		Session session = getCurrentSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		List<?> results = criteria.list();
		Page page = new Page();
		page.setResults(results);
		
		// 行数
		criteria.setProjection(Projections.rowCount());
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		List<?> rowCountList = criteria.list();
		if (rowCountList.size() > 0) {
			page.setTotalCounts((Long) rowCountList.get(0));
		}
		return page;
	}

	protected <T> List<T> findByCriteria(DetachedCriteria criteria) {
		try {
			return (List<T>) getHibernateTemplate().findByCriteria(criteria);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
	public Serializable save(Object transientInstance) {
		return hibernateTemplate.save(transientInstance);
	}

	
	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
	}

	public <T> T findById(Serializable id, Class<T> entityClass) {
		return hibernateTemplate.get(entityClass, id);
	}

	public <T> T merge(T detachedInstance) {
		Object result = getHibernateTemplate().merge(detachedInstance);
		return (T) result;
	}

	public <T> List<T> findByProperty(PropertyMap propertyMap, Class<T> entityClass) {
		return findByProperty(propertyMap, null, entityClass);
	}

	public <T> List<T> findByProperty(PropertyMap propertyMap, SortOrders sortOrders,
			Class<T> entityClass) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		if(propertyMap != null) {
			for(Pair<String, Object> pair : propertyMap) {
				String property = pair.getLeft();
				Object value = pair.getRight();
				if(value == null) {
					criteria.add(Restrictions.isNull(property));
				}
				else {
					criteria.add(Restrictions.eq(property, value));
				}
			}
		}
		if(sortOrders != null) {
			for(Pair<SortDir, String> pair : sortOrders) {
				if(pair.getLeft() == SortDir.ASC) {
					criteria.addOrder(Order.asc(pair.getRight()));
				}
				else {
					criteria.addOrder(Order.desc(pair.getRight()));
				}
			}
		}
		return (List<T>) hibernateTemplate.findByCriteria(criteria);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		String classSimpleName = entityClass.getSimpleName();
		String queryString = "from " + classSimpleName;
		return (List<T>) getHibernateTemplate().find(queryString);
	}
}
