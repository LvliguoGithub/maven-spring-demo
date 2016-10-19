package net.demo.llg.common.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 表达排序条件	<br/>
 * eg.	<br/>
 *     SortOrders so = new SortOrders();	<br/>
 *     so.asc("property1");	<br/>
 *     so.desc("property2");	<br/>
 *     
 * history:
 */
public class SortOrders implements Iterable<Pair<SortDir, String>>{
	
	// dir -> propertyName
	private List<Pair<SortDir, String>> orders;
	
	public SortOrders() {
		orders = new ArrayList<>(5);
	}
	
	public void asc(String propertyName) {
		Pair<SortDir, String> pair = new Pair<>(SortDir.ASC, propertyName);
		orders.add(pair);
	}
	
	public void desc(String propertyName) {
		Pair<SortDir, String> pair = new Pair<>(SortDir.DESC, propertyName);
		orders.add(pair);
	}

	@Override
	public Iterator<Pair<SortDir, String>> iterator() {
		return orders.iterator();
	}
}
