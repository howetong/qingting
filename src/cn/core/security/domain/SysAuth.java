package cn.core.security.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import cn.core.domain.ParentChildrenBean;
/**
 * @function 权限模型，实体类
 * @author howeTong
 */
@Entity
@Table(name="Sys_Auth")
public class SysAuth extends ParentChildrenBean<SysAuth>{
	private static final long serialVersionUID = 1L;
	/**
	 * 所拥有的菜单权限
	 */
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="SYS_Auth_Menu",joinColumns={@JoinColumn(name="auth_id")},inverseJoinColumns={@JoinColumn(name="menu_id")})
	private List<SysMenu> menus = new ArrayList<>();

	public SysAuth() {
		super();
	}
	
	public SysAuth(Long id) {
		super(id);
	}
	
	public SysAuth(Long id,String name) {
		super(id,name);
	}

	@Cacheable(value="Sys_Auth_menus_cache")
	public List<SysMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenu> menus) {
		this.menus = menus;
	}
}
