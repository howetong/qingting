package cn.core.security.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.controller.CRUDController;
import cn.core.security.domain.SysUser;
import cn.core.security.service.ISysUserService;


@Controller
@RequestMapping("/admin/user")
public class SysUserController extends CRUDController<SysUser>{
	@Resource
	private ISysUserService service;
	
	@Resource
	public void setUserService(ISysUserService service) {
		this.service = service;
		super.service = service;
	}
	
	/**
	 * 
	 *设置系统管理模块路径
	 */
	protected void beforeInitBinder(WebDataBinder binder){
		super.setModuleDirectoryView("sys");
		super.beforeInitBinder(binder);	
	}
	
	protected void beforeSaveNew(SysUser entity){
		super.beforeSaveNew(entity);
	}
	
	protected void beforeSaveUpdate(SysUser entity,SysUser dest){
		if(entity != null){
			if(entity.getPassword() != null && !"".equals(entity.getPassword())){
				
				SysUser user = service.updatePWD(entity.getId(),entity.getPassword());
				entity.setPassword(user.getPassword());
				entity.setSalt(user.getSalt());
			}
			entity.setLastLoginIp(null);
			entity.setLastLoginTime(null);
			entity.setLoginCount(null);
			entity.setLoginErrorCount(null);
		}
	}
	
	protected void beforeList(SysUser entity){
		super.beforeList(entity);
	}
	
}
