package cn.core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import cn.core.domain.BaseBean;
import cn.core.security.core.CaptchaFormAuthenticationFilter;
import cn.core.security.domain.SysUser;
import cn.core.service.base.IBaseService;
import cn.core.utils.FastJSONHelper;
import cn.core.utils.LoggerUtils;
import cn.core.utils.MyRegExp;
import cn.core.utils.RequestUtils;
import cn.core.web.domain.SysConfig;


@MappedSuperclass
public class BaseController<E extends BaseBean> {
	
	protected Logger log = Logger.getLogger(this.getClass());
	public static final String CMD_EDIT = "edit";
	public static final String CMD_NEW = "new";
	public static final String MODEL = "model";
	protected static final String idField = BaseBean.ID_FIELD;
	/**
	 * 状态删除字段 delStatus ,boolean型
	 */
	protected static final String delField = BaseBean.DEL_STATUS_FIELD;
	/**
	 * 状态字段 status ,boolean型
	 */
	protected static final String statusField = BaseBean.STATUS_FIELD;
	
	/**
	 * 文件分隔符 \ or /
	 */
	protected static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	/**
	 * 路径分隔符 ;
	 */
	protected static final String PATH_SEPARATOR = System
			.getProperty("path.separator");
	/**
	 * 行分隔符 /r/n
	 */
	protected static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	
	/**
	 * 当前登录用户
	 */
	public final static String CURRENT_ADMIN_USER = "current_admin_user";
	
	/**
	 * jsp页面视图模板主文件
	 */
	private String pageDirectoryView = "default";
	/** 	
	 * jsp页面视图模板块模板文件夹 
	 */
	private String moduleDirectoryView = "";
	/**
	 * 系统配置
	 */
	private SysConfig siteConfig;
	
	/**
	 * 当前页面操作主数据模型
	 */
	protected Class<E> entityClass;
	
	/**
	 * 通用service
	 */
	@Resource(name = "baseSimpleServiceImpl")
	protected IBaseService service;
	
	@SuppressWarnings("unchecked")
	public BaseController() {
		Class<?> clz = getClass();

		Type tp = clz.getGenericSuperclass();
		if (tp instanceof ParameterizedType) {
			// 获取泛型化的父类
			ParameterizedType type = (ParameterizedType) tp;
			// 获取泛型运行期真实的类型
			this.entityClass = (Class<E>) type.getActualTypeArguments()[0];

		}
	}
	
	public Class<E> getEntityClass() {
		return entityClass;
	}
	
	protected String getPageDirectoryView(){
		return pageDirectoryView + FILE_SEPARATOR + getModuleDirectoryView();
	}

	public void setPageDirectoryView(String pageDirectoryView) {
		this.pageDirectoryView = pageDirectoryView;
	}

	public String getModuleDirectoryView() {
		if (null != this.moduleDirectoryView && !"".equals(this.moduleDirectoryView)) {
			return moduleDirectoryView + FILE_SEPARATOR;
		}
		return moduleDirectoryView;
	}

	public void setModuleDirectoryView(String moduleDirectoryView) {
		this.moduleDirectoryView = moduleDirectoryView;
	}

	public SysConfig getSiteConfig() {
		return siteConfig;
	}

	public void setSiteConfig(SysConfig siteConfig) {
		this.siteConfig = siteConfig;
	}
	
	/**
	 * 当前登录用户
	 */
	protected SysUser user = null;

	protected void info(int num,Object obj){
		LoggerUtils.info(getClass(), num, obj);
	}
	
	protected void debug(int num,Object obj){
		LoggerUtils.debug(getClass(), num, obj);
	}
	
	protected void error(int num,Object obj){
		LoggerUtils.error(getClass(), num, obj);
	}
	
	/**
	 * MVC初始化
	 * @return
	 */
	/*@InitBinder
	public void initBinder(WebDataBinder binder){
		beforeInitBinder(binder);
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
		this.setSiteConfig(SiteConfig.getConfig());
		if(null != siteConfig.getTemplate() && this.getPageDirectoryView() != siteConfig.getTemplate()){
			this.setPageDirectoryView(siteConfig.getTemplate());
		}
		afterInitBinder(binder);
	}*/
	/**
	 * MVC初始化之前
	 * @return
	 */
	protected void beforeInitBinder(WebDataBinder binder){
		
	}
	
	/**
	 * MVC初始化之后
	 * @return
	 */
	protected void afterInitBinder(WebDataBinder binder){
		
	}

	/**
	 * 当前登录用户
	 * 
	 * @return SysUser
	 */
	protected SysUser getCurrentSysUser(){
		if (user == null) {
			user = (SysUser) RequestUtils.getSession(CURRENT_ADMIN_USER);
		}
		return user;
	}
	
	/**
	 * 设置http页面默认视图模型
	 */
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public ModelAndView doView(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "v", required = false) String v){
		LoggerUtils.error(getClass(), 103, "doDefault..."+getClass().getSimpleName().toString().replace("Sys", "")
				.replace("Controller", "").toLowerCase());
		ModelAndView mv = new ModelAndView();
		beforeDefault(mv);
		/*mv.addObject("user",getCurrentSysUser());*/
		mv.setViewName(getPageDirectoryView()+getClass().getSimpleName().toString().replace("Sys", "")
				.replace("Controller", "").toLowerCase());
		error(59, getPageDirectoryView()+getClass().getSimpleName().toString().replace("Sys", "")
				.replace("Controller", "").toLowerCase());
		afterDefault(mv);
		return mv;
	}
	
	/**
	 * 获得当前的访问路径
	 */
	protected String getCurrentUrl(){
		HttpServletRequest request = this.getRequest();
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String orgiUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), orgiUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}
	
	protected HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	protected String getLoginUrl(){
		return (String)getSession(CaptchaFormAuthenticationFilter.SESSION_ADMIN_LOGIN_URL);
	}
	
	/**
	 * 
	 * 得到session中的对象
	 */
	protected Object getSession(String key){
		return getRequest().getSession().getAttribute(key);
	}
	
	protected<T> void writeJSON(HttpServletResponse response,T obj) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String json = FastJSONHelper.serialize(obj);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	/**
	 * 是否为非0的整形数字
	 */
	protected boolean isNumberNot0(String val){
		return MyRegExp.isNumberNot0(val);
	}
	
	protected void beforeDefault(ModelAndView mv){
		
	}
	
	protected void afterDefault(ModelAndView mv){
		
	}
	
	/**
	 * 获取请求参数转化为map
	 */
	@SuppressWarnings("rawtypes")
	
	protected Map<String, Object> getParameterMap(){
		Map<String, String[]> properties = getRequest().getParameterMap();
		Iterator<Entry<String,String[]>> entries = properties.entrySet().iterator();
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Map.Entry entry;
		String name = "";
		String value = "";
		while(entries.hasNext()){
			entry = (Map.Entry)entries.next();
			name = (String)entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0,value.length() - 1);
			}else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
}
