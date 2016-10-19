package net.demo.llg.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Page.java
 *
 * abstract: 分页查询结果
 *
 * history:
 * 	
 */
public class Page {
	
	/**
	 * 分页记录列表
	 */
	private List results;
	
	/**
	 * 分页记录数
	 */
	private long totalCounts;
	
	public Page() {
		results = new ArrayList<>(0);
	}
	
	@Override
	public String toString() {
		return "Page [totalCounts=" + totalCounts + ", results=" + results + "]";
	}

	public List getResults() {
		return results;
	}
	
	public void setResults(List results) {
		this.results = results;
	}
	
	public long getTotalCounts() {
		return totalCounts;
	}
	
	public void setTotalCounts(long totalCounts) {
		this.totalCounts = totalCounts;
	}

}
