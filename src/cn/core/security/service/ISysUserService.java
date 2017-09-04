package cn.core.security.service;

import cn.core.security.domain.SysUser;
import cn.core.service.base.IBaseService;

public interface ISysUserService extends IBaseService{
	/**
	 * 修改密码，对象更新
	 */
	public SysUser updatePWD(SysUser user);

	/**
	 * 修改密码
	 * 
	 * @return SysUser
	 */
	public SysUser updatePWD(Long id, String password);

}
