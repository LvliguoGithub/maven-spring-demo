package net.demo.llg.common.util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * CriteriaUtil.java
 *
 * abstract: Criteria常用重复性代码整合
 *
 * history:
 * 
 * mis_llg 2016年5月25日 初始化
 */
public class CriteriaUtil {

	/**
	 * 设置ilike查询条件
	 * 
	 * @param criteria
	 * @param prop
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	public static void buildPropIlike(DetachedCriteria criteria, String prop, String value) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(prop)) {
			criteria.add(Restrictions.ilike(prop, value.trim(), MatchMode.ANYWHERE));
		}
	}

	/**
	 * 设置ilike查询条件（数组）
	 * 
	 * @param criteria
	 * @param props
	 *            属性数组
	 * @param values
	 *            值数组
	 * @return
	 */
	public static void buildPropsIlike(DetachedCriteria criteria, String[] props, String[] values) {
		buildProps(criteria, props, values, "Ilike");
	}

	/**
	 * 设置in查询条件
	 * 
	 * @param criteria
	 * @param prop
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	public static void buildPropIn(DetachedCriteria criteria, String prop, String value) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(prop)) {
			String[] array = value.split(",");
			criteria.add(Restrictions.in(prop, array));
		}
	}

	/**
	 * 设置In查询条件（数组）
	 * 
	 * @param criteria
	 * @param props
	 *            属性数组
	 * @param values
	 *            值数组
	 * @return
	 */
	public static void buildPropsIn(DetachedCriteria criteria, String[] props, String[] values) {
		buildProps(criteria, props, values, "In");
	}

	/**
	 * 设置eq查询条件
	 * 
	 * @param criteria
	 * @param prop
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	public static void buildPropEq(DetachedCriteria criteria, String prop, Object value) {
		if (value != null && StringUtils.isNotBlank(prop)) {
			criteria.add(Restrictions.eq(prop, value));
		}
	}

	/**
	 * 设置OrLike查询条件
	 * 
	 * @param criteria
	 * @param prop
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	public static void buildPropOrLike(DetachedCriteria criteria, String prop1, String prop2, String value) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(prop1) && StringUtils.isNotBlank(prop2)) {
			criteria.add(Restrictions.or(Restrictions.ilike(prop1, value.trim(), MatchMode.ANYWHERE),
					Restrictions.ilike(prop2, value.trim(), MatchMode.ANYWHERE)));
		}
	}

	/**
	 * 设置In查询条件（数组）
	 * 
	 * @param criteria
	 * @param props1
	 *            属性数组
	 * @param props2
	 *            属性数组
	 * @param values
	 *            值数组
	 * @return
	 */
	public static void buildPropsOrLikes(DetachedCriteria criteria, String[] props1, String[] props2, String[] values) {
		buildPropsOr(criteria, props1, props2, values, "Ilike");
	}

	/**
	 * 设置查询条件（数组）
	 * 
	 * @param criteria
	 * @param props
	 *            属性数组
	 * @param values
	 *            值数组
	 * @return
	 */
	private static void buildProps(DetachedCriteria criteria, String[] props, String[] values, String type) {
		if (criteria != null && props != null && values != null) {
			if (props.length > 0 && props.length == values.length) {
				for (int i = 0; i < props.length; i++) {
					if ("In".equals(type)) {
						buildPropIn(criteria, props[i], values[i]);
					} else if ("Ilike".equals(type)) {
						buildPropIlike(criteria, props[i], values[i]);
					} else {
						buildPropEq(criteria, props[i], values[i]);
					}
				}
			} else {
				throw new RuntimeException("属性与值的个数不相等");
			}
		} else {
			throw new RuntimeException("存在参数为空");
		}
	}

	/**
	 * 设置查询条件（数组）
	 * 
	 * @param criteria
	 * @param props1
	 *            属性数组1
	 * @param props2
	 *            属性数组2
	 * @param values
	 *            值数组
	 * @return
	 */
	private static void buildPropsOr(DetachedCriteria criteria, String[] props1, String[] props2, String[] values,
			String type) {
		if (criteria != null && props1 != null && props2 != null && values != null) {
			if (props1.length > 0 && props1.length == props2.length && props1.length == values.length) {
				for (int i = 0; i < props1.length; i++) {
					if ("Ilike".equals(type)) {
						buildPropOrLike(criteria, props1[i], props2[i], values[i]);
					}
				}
			} else {
				throw new RuntimeException("两个属性数目不等，或属性与值的个数不相等");
			}
		} else {
			throw new RuntimeException("存在参数为空");
		}
	}
}
