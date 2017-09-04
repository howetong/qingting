package cn.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.core.dao.IBaseSimpleDao;
import cn.core.service.IBaseSimpleService;
import cn.core.service.base.BaseServiceImpl;
/**
 * 
 * @description 业务逻辑服务实现层
 * @author howeTong
 */
@Service
public class BaseSimpleServiceImpl extends BaseServiceImpl implements IBaseSimpleService {
	protected IBaseSimpleDao dao;

	@Resource(name="baseSimpleDaoImpl")
	public void setDao(IBaseSimpleDao dao) {
		this.dao = dao;
		super.dao = dao;
	}
	
}
