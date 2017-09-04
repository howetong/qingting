package cn.core.domain.support;

import java.util.List;

public class PageView<E> {
	/**
	 * dataTable需要的数据格式
	 */
	private List<E> rows;
	/**
	 * total page
	 */
	private long maxResult = 12;
	/**current page 当前页 */
	private long currentPage = 1;
	/** total record 总记录  **/
	private long total;
	
	public PageView() {
	}
	
	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		setCurrentPage(currentPage);
	}

	public void setQueryResult(QueryResult<E> qr){
		setRows(qr.getResultList());
		setCurrentPage(currentPage);
	}
	
	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public long getTotalPage() {
		long m = 0;
		if ((this.getTotal() % this.maxResult) > 0) {
			m = 1;
		}
		return this.getTotal() / this.maxResult + m;
	}

	public long getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(long maxResult) {
		this.maxResult = maxResult;
	}

	public long getCurrentPage() {
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
		if (this.currentPage > this.getTotalPage()) {
			this.currentPage = this.getTotalPage();
		}
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
		if (this.currentPage > this.getTotalPage()) {
			this.currentPage = this.getTotalPage();
		}
		this.currentPage = currentPage;
	}

	public long getFirstPage() {
		long firstPage = (getCurrentPage() - 1);
		long p = (firstPage < 1 ? 1 : firstPage);
		return p > getTotalPage() ? getTotalPage() : p;
	}

	public long getLastPage() {
		long lastPage = (getCurrentPage() + 1);

		return (lastPage > this.getTotalPage() ? this.getTotalPage() : lastPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PageView [getTotalRecord()=" + getTotal()
				+ ", getRecords()=" + getRows() + ", getTotalPage()="
				+ getTotalPage() + ", getMaxResult()=" + getMaxResult()
				+ ", getCurrentPage()=" + getCurrentPage()
				+ ", getFirstPage()=" + getFirstPage() + ", getLastPage()="
				+ getLastPage() + "]";
	}

}
