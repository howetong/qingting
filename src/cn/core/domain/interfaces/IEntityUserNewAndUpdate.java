package cn.core.domain.interfaces;

import cn.core.security.domain.SysUser;

/**
 * 
 * @description 数据模型创建和修改用户信息规范定义
 * 要实现数据模型创建和修改用户信息的数据自动绑定,前台提交后能自动保存与修改（如创建者和修改者）,数据模型就必须实现此接口 <br>
 * 并且属性名称必须是createUser和updateUser,前台提交数据无效,由系统后台控制<br>
 * @author howeTong
 */
public interface IEntityUserNewAndUpdate {
	/**
	 * 创建用户属性 createUser.id
	 * 类型 SysUser
	 */
	public static final String CREATE_USER = "createUser";
	
	/**
	 * 修改用户属性 updateUser.id
	 * 类型 SysUser
	 */
	public static final String UPDATE_USER = "updateUser";
	
	/**
	 * 获取创建用户
	 * @return
	 */
	public SysUser getCreateUser();
	
	/**
	 * 设置创建用户
	 * @param createUser
	 */
	public void setCreateUser(SysUser createUser);
	
	/**
	 * 获取修改用户
	 * @return
	 */
	public SysUser getUpdateUser();
	
	/**
	 * 设置修改用户
	 * @param updateUser
	 */
	public void setUpdateUser(SysUser updateUser);
	
}
