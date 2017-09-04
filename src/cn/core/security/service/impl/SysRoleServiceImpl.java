package cn.core.security.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.core.security.domain.SysRole;
import cn.core.security.service.ISysRoleService;
import cn.core.service.base.BaseServiceImpl;
import cn.core.web.dao.ISysRoleDao;

@SuppressWarnings(value = "all")
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements ISysRoleService{

	@Resource
	private ISysRoleDao roleDao;
	
	@Resource
	public void setRoleDao(ISysRoleDao roleDao){
		this.roleDao = roleDao;
		this.dao = roleDao;
	}
    
}
