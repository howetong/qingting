package cn.core.domain.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * function 基本参数模型，主要是分页、排序等参数
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class BaseParameter implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String SORTED_ASC = "ASC";
	public static final String SORTED_DESC = "DESC";
	/**
	 * 页码
	 */
	@JSONField(serialize = false)
	private Integer page = 1;
	/**
	 * 每页数据记录数
	 */
	@JSONField(serialize = false)
	private Integer rows = 10;
	/**
	 * top n 数据
	 */
	@JSONField(serialize = false)
	private Integer topCount;
	/**
	 * 字段分类
	 */
	@JSONField(serialize = false)
	private String[] sortColumns;
	/**
	 * 操作指令
	 */
	@JSONField(serialize = false)
	private String cmd;
	/**
	 * 数据库操作查询种类
	 */
	@JSONField(serialize = false)
	private String flag = "AND";
	
	@JSONField(serialize = false)
	private Map<String,Object> queryDynamicConditions = new HashMap(4);
	/**
	 * 数据库操作排序字段
	 */
	@JSONField(serialize = false)
	private Map<String,String> sortedConditions = new LinkedHashMap(4);
	
	@JSONField(serialize = false)
	private Map<String,Object> dynamicProperties = new HashMap(4);

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if(page == null || page == 0){
			page = 1;
		}
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getTopCount() {
		return topCount;
	}

	public void setTopCount(Integer topCount) {
		this.topCount = topCount;
	}

	@JSONField(serialize = false)
	public String getSortColumnsString(){
		StringBuffer sb = new StringBuffer();
		if(this.sortColumns != null){
			for(String s : this.sortColumns){
				sb.append("$sortColumns="+s);
			}
		}
		return sb.toString();
	}
	
	public String[] getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String[] sortColumns) {
		this.sortColumns = sortColumns;
		if(sortColumns != null){
			for(String s : sortColumns){
				String[] sa = s.split("\\|");
				if(sa.length == 2){
					this.sortedConditions.put(sa[0], sa[1]);
				}
			}
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Map<String, Object> getQueryDynamicConditions() {
		return queryDynamicConditions;
	}

	public void setQueryDynamicConditions(Map<String, Object> queryDynamicConditions) {
		this.queryDynamicConditions = queryDynamicConditions;
	}

	public Map<String, String> getSortedConditions() {
		return sortedConditions;
	}

	public void setSortedConditions(Map<String, String> sortedConditions) {
		this.sortedConditions = sortedConditions;
	}

	public Map<String, Object> getDynamicProperties() {
		return dynamicProperties;
	}

	public void setDynamicProperties(Map<String, Object> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}
	//后一页第一条记录index
	public Integer getLastResult(){
		return this.getRows() * this.getPage();
	}
	//前一页最后一条记录index
	public Integer getFirstResult(){
		return this.getRows() * (this.getPage()-1);
	}
	
}
