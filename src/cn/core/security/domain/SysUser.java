package cn.core.security.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.cache.annotation.Cacheable;

import com.alibaba.fastjson.annotation.JSONField;

import cn.core.domain.UserNewEditBean;
import cn.core.domain.interfaces.IEntityUserNewAndUpdate;
/**
 * @function 系统用户模型。用户具有角色、用户组
 * @author howeTong
 */
@Entity
@Table(name="Sys_User")
public class SysUser extends UserNewEditBean implements IEntityUserNewAndUpdate{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	@JSONField(serialize = false)
	private String password;
	/**
	 * 盐是用户名+随机数
	 */
	@JSONField(serialize = false)
	private String salt;
	/**
	 * 登陆次数
	 */
	@Column(columnDefinition = "INT default 0")
	private Integer loginCount = 0;
	/**
	 * 登陆错误次数
	 */
	@Column(columnDefinition = "INT default 0")
	private Integer loginErrorCount = 0;
	/**
	 * 最后一次登录ip
	 */
	private String lastLoginIp;
	/**
	 * 最后一次登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;
	
	/**
	 * 所拥有的角色
	 */
	//CascadeType.REFRESH级联刷新：获取order对象里也同时也重新获取最新的items时的对象。对应EntityManager的refresh(object)方法有效。即会重新查询数据库里的最新数据
	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="Sys_Role_User",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
	//@JSONField(serialize = false)
	private List<SysRole> roles = new ArrayList<SysRole>();
	
	private Boolean allRole = false;
	
	public SysUser() {
		super();
	}
	
	public SysUser(Long id) {
		super(id);
	}
	
	public SysUser(Long id,String name) {
		super(id,name);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}

	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Cacheable(value="Sys_User_roles_cache")
	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	
	public Boolean getAllRole() {
		return allRole;
	}

	public void setAllRole(Boolean allRole) {
		this.allRole = allRole;
	}

}
