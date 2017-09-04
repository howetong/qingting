package cn.core.security.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import com.alibaba.fastjson.annotation.JSONField;

import cn.core.domain.ParentChildrenBean;

/**
 * @function 系统角色权限实体类
 * @author howeTong
 *
 */
@Entity
@Table(name="Sys_Role")
public class SysRole extends ParentChildrenBean<SysRole>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 所有权限字段 boolean
	 */
	public static final String ALL_AUTH = "allAuth";
	
	/**
	 * 所拥有的权限
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="Sys_Auth_Role",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="auth_id")})
	private List<SysAuth> auths = new ArrayList<>();
	
	
	/**
	 * 所拥有的用户
	 */
	@ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.REFRESH })
	@JoinTable(name="Sys_Role_User",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	//角色拥有的用户不需要发往前端
	@JSONField(serialize = false)
	private List<SysUser> users = new ArrayList<SysUser>();
	
	/**
	 * 所有权限
	 */
	private Boolean allAuth = false;
	
	public SysRole() {
		super();
	}
	
	public SysRole(Long id) {
		super(id);
	}
	
	public SysRole(Long id,String name) {
		super(id,name);
	}
	
	@Cacheable(value="Sys_Role_auths_cache")
	public List<SysAuth> getAuths() {
		return auths;
	}
	public void setAuths(List<SysAuth> auths) {
		this.auths = auths;
	}

	@Cacheable(value="Sys_Role_users_cache")
	public List<SysUser> getUsers() {
		return users;
	}
	public void setUsers(List<SysUser> users) {
		this.users = users;
	}

	public Boolean getAllAuth() {
		return allAuth;
	}

	public void setAllAuth(Boolean allAuth) {
		this.allAuth = allAuth;
	}
	
}
