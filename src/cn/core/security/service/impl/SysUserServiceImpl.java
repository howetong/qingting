package cn.core.security.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.core.security.commons.Digests;
import cn.core.security.commons.Encodes;
import cn.core.security.domain.SysUser;
import cn.core.security.service.ISysUserService;
import cn.core.service.base.BaseServiceImpl;
import cn.core.web.dao.ISysRoleDao;
import cn.core.web.dao.ISysUserDao;

/**
 * @function 系统用户service
 * @author howeTong
 *
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements ISysUserService{
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	private Class<SysUser> entityClass = SysUser.class;
	
	/**
	 * 当前用户
	 */
	public static final String ADMIN_CURRENT_USER = "admin_Current_User";
	protected ISysUserDao userDao;
	protected ISysRoleDao roleDao;

	@Resource
	public void setUserDao(ISysUserDao userDao){
		this.userDao = userDao;
		this.dao = userDao;
	}
	
	@Resource
	public void setRoleDao(ISysRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	
	/**
	 * 修改密码
	 */
	@Override
	public SysUser updatePWD(SysUser user){
		entryptPassword(user);
		super.update(user);
		return user;
	}
	
	/**
	 * 加密密码
	 */
	public void save(SysUser user){
		entryptPassword(user);
		super.save(user);
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次sha-1 hash
	 */
	private void entryptPassword(SysUser user){
		if (user != null && null != user.getPassword() && !"".equals(user.getPassword())) {
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			user.setSalt(Encodes.encodeHex(salt));
			byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(),salt,HASH_INTERATIONS);
			user.setPassword(Encodes.encodeHex(hashPassword));
		}
	}

	/**
	 * 修改密码
	 * 
	 * @return SysUser
	 */
	@Override
	public SysUser updatePWD(Long id, String password) {
		SysUser user = super.get(entityClass, id);
		user.setPassword(password);
		entryptPassword(user);
		super.update(user);
		return user;
	}

}
