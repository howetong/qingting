package cn.core.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.core.security.domain.SysMenu;
import cn.core.security.service.ISysMenuService;
import cn.core.service.base.BaseServiceImpl;
import cn.core.web.dao.ISysMenuDao;

@SuppressWarnings(value = "all")
@Service
public class SysMenuServiceImpl extends BaseServiceImpl implements ISysMenuService{

	@Resource
	private ISysMenuDao menuDao;
	
	@Resource
	public void setMenuDao(ISysMenuDao menuDao){
		this.menuDao = menuDao;
		this.dao = menuDao;
	}
    
}
