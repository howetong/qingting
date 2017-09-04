package cn.core.domain.support;

import java.util.List;

public class QueryResult<E> {
	/**
	 * 结果�?
	 */
	private List<E> resultList;
	/**
	 * 总记�?
	 */
	private Long totalCount;
	public List<E> getResultList() {
		return resultList;
	}
	public void setResultList(List<E> resultList) {
		this.resultList = resultList;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
