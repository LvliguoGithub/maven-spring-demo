package net.demo.llg.common.dao;

import java.io.Serializable;
import java.util.List;

import net.demo.llg.common.page.Page;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;


/**
 * 
 * GenericDao.java
 *
 * abstract: 泛Entity类型DAO
 *
 * history:
 * 
 */
@Component("genericDao")
public class GenericDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	private final String alias = "_this";
	private static Logger log = LoggerFactory.getLogger(GenericDao.class);

	protected HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public void save(Object transientInstance) {
		log.debug("saving entity: " + transientInstance.getClass().getSimpleName());
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.debug("deleting entity: " + persistentInstance.getClass().getSimpleName());
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public <T> void deleteById(Serializable id, Class<T> clazz) {
		log.debug("deleteById:" + id + ",class:" + clazz.getName());
		try {
			T instance = findById(id, clazz);
			delete(instance);
		} catch (RuntimeException re) {
			log.error("deleteById failed", re);
			throw re;
		}

	}

	public <T> T findById(Serializable id, Class<T> entityClass) {
		log.debug("getting " + entityClass.getSimpleName() + " instance with id: " + id);
		try {
			Object instance = getHibernateTemplate().get(entityClass.getName(), id);
			return (T) instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public <T> List<T> findByProperty(String propertyName, Object value, Class<T> entityClass) {
		String classSimpleName = entityClass.getSimpleName();
		log.debug("finding " + classSimpleName + " instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from " + classSimpleName + " as model where model." + propertyName + "= ?";
			return (List<T>) getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		String classSimpleName = entityClass.getSimpleName();
		log.debug("finding all " + classSimpleName + " instances");
		try {
			String queryString = "from " + classSimpleName;
			return (List<T>) getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public <T> T merge(T detachedInstance) {
		String classSimpleName = detachedInstance.getClass().getSimpleName();
		log.debug("merging " + classSimpleName + " instance");
		try {
			Object result = getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return (T) result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public <T> List<T> findByCriteria(DetachedCriteria criteria) {
		log.debug("finding by detached criteria");
		try {
			return (List<T>) getHibernateTemplate().findByCriteria(criteria);
		} catch (RuntimeException re) {
			log.error("find by detached criteria failed", re);
			throw re;
		}
	}

	public <T> DetachedCriteria getProjectCriteria(String[] props, Class<T> clazz) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz, alias);
		ProjectionList pList = Projections.projectionList();
		for (String prop : props) {
			pList.add(Property.forName(alias + "." + prop).as(prop));
		}
		criteria.setProjection(pList);
		criteria.setResultTransformer(Transformers.aliasToBean(clazz));
		return criteria;
	}

	public Page getScrollDataByCriteria(int start, int limit, DetachedCriteria detachedCriteria) {
		return getPage(start, limit, detachedCriteria);
	}
	
	protected Page getPage(int start, int limit, DetachedCriteria detachedCriteria) {
		start = Math.max(0, start);
		limit = Math.max(0, limit);
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
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
	
	public void flushSession() {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		session.flush();
		
	}
}
