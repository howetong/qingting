package cn.core.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.cache.annotation.Cacheable;

import cn.core.security.domain.SysUser;
import cn.core.web.domain.SysConfig;

/**
 * @function 新建编辑数据抽象模型类
 * @author howeTong
 *
 */
@MappedSuperclass
public abstract class UserNewEditBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 创建者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private SysUser createUser;
	/**
	 * 修改者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private SysUser updateUser;
	/**
	 * 指标来源
	 */
	@ManyToOne(targetEntity=SysConfig.class,optional = true,fetch = FetchType.EAGER)
	private BeanSite site;
	
	public UserNewEditBean() {
		super();
	}
	
	public UserNewEditBean(Long id) {
		super(id);
	}
	
	public UserNewEditBean(Long id,String name) {
		super(id,name);
	}
	
	@Cacheable(value="createUser")
	public SysUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}

	@Cacheable(value="updateUser")
	public SysUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(SysUser updateUser) {
		this.updateUser = updateUser;
	}

	public BeanSite getSite() {
		return site;
	}

	public void setSite(BeanSite site) {
		this.site = site;
	}
}
