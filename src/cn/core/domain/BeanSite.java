package cn.core.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.cache.annotation.Cacheable;

import cn.core.web.domain.SysConfig;

@MappedSuperclass
@Cacheable(value="beanSite")
public class BeanSite extends BaseBean{
	private static final long serialVersionUID = 1L;
	/**
	 * 本站状态字段
	 */
	public static final String LOCAL_FIELD = "local";
	/**
	 * 系统url
	 */
	private String url;
	/**
	 * 是否是本站
	 */
	private Boolean local = false;
	/**
	 * 部署地址
	 */
	private String deployAddress;
	/**
	 * 服务器ip
	 */
	private String serviceIp;
	/**
	 * 服务器端口
	 */
	private int servicePort;
	/**
	 * 服务器级别
	 */
	private int serviceLevel;
	/**
	 * 父服务器
	 */
	@ManyToOne(targetEntity=SysConfig.class,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private BeanSite parentBeanSite;
	
	public BeanSite() {
		super();
	}
	
	public BeanSite(Long id) {
		super(id);
	}
	
	public BeanSite(Long id,String name) {
		super(id,name);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(Boolean local) {
		this.local = local;
	}

	public String getDeployAddress() {
		return deployAddress;
	}

	public void setDeployAddress(String deployAddress) {
		this.deployAddress = deployAddress;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	public int getServicePort() {
		return servicePort;
	}

	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	public int getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(int serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public BeanSite getParentBeanSite() {
		return parentBeanSite;
	}

	public void setParentBeanSite(BeanSite parentBeanSite) {
		this.parentBeanSite = parentBeanSite;
	}
}
