/**
 * 
 */
package net.demo.llg.common.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 表达匹配条件 <br/>
 * eg.PropertyMap map = new PropertyMap();  <br/>
 *    map.add("propertyName", value);	<br/>
 *    map.add("propertyName2", null);    //可以表达null条件	<br/>
 *    
 * history:
 */
public class PropertyMap implements Iterable<Pair<String, Object>>{
	private List<Pair<String, Object>> pairs;
	
	public PropertyMap() {
		pairs = new ArrayList<>(10);
	}
	
	public void add(String propertyName, Object value) {
		Pair<String, Object> pair = new Pair<>(propertyName, value);
		pairs.add(pair);
	}

	@Override
	public Iterator<Pair<String, Object>> iterator() {
		return pairs.iterator();
	}
	
}
