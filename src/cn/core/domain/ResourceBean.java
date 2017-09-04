package cn.core.domain;

import javax.persistence.MappedSuperclass;

/**
 * @function 访问资源继承处理
 * @author th
 *
 */
@MappedSuperclass
public abstract class ResourceBean<T> extends UserNewEditBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 访问连接
	 */
	private String url = "#";
	
	public ResourceBean() {
		super();
	}
	
	public ResourceBean(Long id) {
		super(id);
	}
	
	public ResourceBean(Long id,String name) {
		super(id,name);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
