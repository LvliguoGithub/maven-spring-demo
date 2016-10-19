package net.demo.llg.common.util;

import java.util.Arrays;
import java.util.List;

import net.demo.llg.common.page.CnOrder;
import org.hibernate.criterion.Order;


/**
 * OrderUtil.java
 *
 * abstract: 排序通用方法，调用处理中文的CnOrder
 *
 * history:
 * 
 * mis_llg 2016年7月11日 初始化
 */
public class OrderUtil {

	public static Order buildOrder(String[] cnOrderProperties, String property, String dir) {
		Order order = null;
		List<String> cnList = Arrays.asList(cnOrderProperties);
		if ("ASC".equals(dir)) {
			if (cnList.contains(property)) {
				order = CnOrder.asc(property);
			} else {
				order = Order.asc(property);
			}
		} else {
			if (cnList.contains(property)) {
				order = CnOrder.desc(property);
			} else {
				order = Order.desc(property);
			}
		}
		return order;
	}
}
