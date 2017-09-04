package cn.core.domain.support;

import java.io.Serializable;
public class KeyValueResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long pid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/* 
	 * @see java.lang.Object#toString()
	 * 覆写父类方法
	 */
	@Override
	public String toString() {
		return "KeyValueResult [id=" + id + ", name=" + name + ", pid=" + pid
				+ "]";
	}
	
}
