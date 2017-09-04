package cn.core.security.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import cn.core.domain.ParentChildrenBean;
@Entity
@Table(name="Sys_Menu")
@Cacheable(value="Sys_Menu_cache")
public class SysMenu extends ParentChildrenBean<SysMenu>{
	
	private static final long serialVersionUID = 1L;
	//图片样式
	private String iconCls = "menu";
	//css样式
	private String className = "icon-bookmark-empty";

	public SysMenu() {
		super();
	}
	
	public SysMenu(Long id) {
		super(id);
	}
	
	public SysMenu(Long id,String name) {
		super(id,name);
	}
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getText(){
		return this.getName();
	}
	
	public boolean getLeaf(){
		return this.getChildren().size() > 0 ? false : true;
	}

}
