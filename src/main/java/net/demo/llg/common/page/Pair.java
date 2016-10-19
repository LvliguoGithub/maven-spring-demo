/**
 * 
 */
package net.demo.llg.common.page;

/**
 * 
 * history:
 */
public class Pair<L, R> {
	private L left;
	private R right;
	
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "Pair [left=" + left + ", right=" + right + "]";
	}

	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
}
