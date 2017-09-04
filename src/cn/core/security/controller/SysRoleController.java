package cn.core.security.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.controller.CRUDController;
import cn.core.security.domain.SysUser;
import cn.core.security.service.ISysRoleService;
/**
 * 
 * @description 系统角色控制器
 * @author howeTong
 */

@Controller
@RequestMapping("/admin/role")
public class SysRoleController extends CRUDController<SysUser>{
	@Resource
	private ISysRoleService service;
	
	/**
	 * 
	 *设置系统管理模块路径
	 */
	protected void beforeInitBinder(WebDataBinder binder){
		super.setModuleDirectoryView("sys");
		super.beforeInitBinder(binder);	
	}
	
	@Resource
	public void setRoleService(ISysRoleService service) {
		this.service = service;
		super.service = service;
	}
	
	
	
}
