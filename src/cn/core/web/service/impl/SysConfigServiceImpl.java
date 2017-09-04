package cn.core.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.core.service.base.BaseServiceImpl;
import cn.core.web.dao.ISysConfigDao;
import cn.core.web.dao.ISysMenuDao;
import cn.core.web.domain.SysConfig;
import cn.core.web.service.ISysConfigService;
@Service
@SuppressWarnings("all")
public class SysConfigServiceImpl extends BaseServiceImpl implements ISysConfigService{
	@Resource
	private ISysConfigDao configDao;
	
	@Resource
	public void setConfigDao(ISysConfigDao configDao){
		this.configDao = configDao;
		this.dao = configDao;
	}

	@Override
	public SysConfig getSite() {
		// TODO Auto-generated method stub
		return null;
	}
}
