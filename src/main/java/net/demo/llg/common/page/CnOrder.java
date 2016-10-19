/**
 * 
 */
package net.demo.llg.common.page;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * 
 * history:
 */
public class CnOrder extends Order implements Serializable{

	private boolean ascending;
	private boolean ignoreCase;
	private String propertyName;
	
	protected CnOrder(String propertyName, boolean ascending) {
		super(propertyName, ascending);
		this.propertyName = propertyName;
		this.ascending = ascending;
	}
	
	public static CnOrder asc(String propertyName) {
		return new CnOrder(propertyName, true);
	}
	
	public static CnOrder desc(String propertyName) {
		return new CnOrder(propertyName, false);
	}
	
	public CnOrder ignoreCase() {
		ignoreCase = true;
		return this;
	}
	
	/**
	 * 在order by子句中加入CONVERT(field using gbk)函数以解决中文排序问题
	 */
	@Override
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		String columns[] = criteriaQuery.getColumnsUsingProjection(criteria,
				propertyName);
		Type type = criteriaQuery
				.getTypeUsingProjection(criteria, propertyName);
		StringBuffer fragment = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			SessionFactoryImplementor factory = criteriaQuery.getFactory();
			boolean lower = ignoreCase && type.sqlTypes(factory)[i] == 12;
			if (lower)
				fragment.append(factory.getDialect().getLowercaseFunction())
						.append('(');
			fragment.append("CONVERT(" + columns[i] + " using GBK)");
			if (lower)
				fragment.append(')');
			fragment.append(ascending ? " asc" : " desc");
			if (i < columns.length - 1)
				fragment.append(", ");
		}
		
		return fragment.toString();
	};
	
	public String toString() {
		return (new StringBuilder()).append(propertyName).append(' ').append(
				ascending ? "asc" : "desc").toString();
	}
}
