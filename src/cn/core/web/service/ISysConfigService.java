package cn.core.web.service;

import cn.core.service.base.IBaseService;
import cn.core.web.domain.SysConfig;
/**
 * @function 站点配置
 * @author howeTong
 *
 */
public interface ISysConfigService extends IBaseService{

	/**
	 * 获取本站
	 */
	SysConfig getSite();

}
