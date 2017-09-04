package cn.core.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import cn.core.domain.BeanSite;

/**
 * @function 系统配置功能
 * @author howeTong
 */
@Entity
@Table(name="Sys_Config")
@Cacheable(value="Sys_Config_cache")
public class SysConfig extends BeanSite{
	private static final long serialVersionUID = 1L;
	/**
	 * 开启指定时间内连续登录次数超过上限不允许登录的控制
	 */
	private Boolean loginControl;
	/**
	 * 连续登录错误次数，不允许再登录
	 * 
	 */
	private Integer loginError = 3;
	/**
	 * 连续登录错误，10分钟后才能登录
	 */
	private Integer loginErrorTime = 10;
	/**
	 * 管理界面模板
	 */
	private String template = "default";
	/**
	 * 上传文件存放目录
	 */
	private String uploadDir = "upload";
	
	
	/**
	 * 系统版本号scale小数点长度
	 */
	@Column(scale = 2,precision = 10)
	private Double versions = 0.1;
	
	public SysConfig(){
		super();
	}
	
	public SysConfig(Long id,String name){
		super(id,name);
	}
	
	public SysConfig(Long id){
		super(id);
	}

	public Boolean getLoginControl() {
		return loginControl;
	}

	public void setLoginControl(Boolean loginControl) {
		this.loginControl = loginControl;
	}

	public Integer getLoginError() {
		return loginError;
	}

	public void setLoginError(Integer loginError) {
		this.loginError = loginError;
	}

	public Integer getLoginErrorTime() {
		return loginErrorTime;
	}

	public void setLoginErrorTime(Integer loginErrorTime) {
		this.loginErrorTime = loginErrorTime;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public Double getVersions() {
		return versions;
	}

	public void setVersions(Double versions) {
		this.versions = versions;
	}
}
