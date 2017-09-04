package cn.core.security.core;

import javax.annotation.Resource;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import cn.core.security.service.ISysUserService;
import cn.core.web.service.ISysConfigService;
/**
 * @function AuthticationFilter自定义登录认证filter
 * @author howeTong
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter{
	
	public static final String  COOKIE_ERROR_REMAINING = "_error_remaining";
	/**
	 * 后台登录成功跳转页
	 */
	public static final String  SESSION_ADMIN_SUCCESS_URL = "admin_SuccessUrl";
	/**
	 * 后台登录页面
	 */
	public static final String  SESSION_ADMIN_LOGIN_URL = "admin_loginUrl";
	/**
	 * 前台登录成功跳转页
	 */
	public static final String  SESSION_FRONT_SUCCESS_URL = "front_SuccessUrl";
	/**
	 * 验证码名称
	 */
	public static final String  CAPTCHA_PARAM = "captcha";
	/**
	 * 返回url
	 */
	public static final String  RETURN_URL = "returnUrl";
	
	@Resource
	private ISysUserService userService;
	
	@Resource
	private ISysConfigService configService;
	
//	@Resource
//	private ISysLogService logService;
//	
//	
//	/**
//	 *创建自定义token
//	 */
//	protected CaptchaUsernamePasswordToken createToken(ServletRequest request,ServletResponse response){
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		String rememberMe = request.getParameter("rememberMe");
//		String captcha = request.getParameter(CAPTCHA_PARAM);
//		LoggerUtils.error(getClass(), 80, "createToken");
//		LoggerUtils.error(getClass(), 81, username);
//		LoggerUtils.error(getClass(), 82, password);
//		return new CaptchaUsernamePasswordToken(username,password,Boolean.parseBoolean(rememberMe),
//				RequestUtils.getIpAddr((HttpServletRequest)request),captcha);
//		
//	}
//	
//	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception{
//		CaptchaUsernamePasswordToken token = createToken(request, response);
//		if (token == null) {
//			throw new IllegalStateException("create AuthenticationToken error");
//		}
//		String successurl = getSuccessUrl();
//		LoggerUtils.error(getClass(), 96, successurl);
//		LoggerUtils.error(getClass(), 97, token);
//		HttpServletRequest req = (HttpServletRequest) request;
//		String username = (String)token.getPrincipal();
//		SysUser user = userService.getByProperties("username",username);
//		//验证码校验
//		if(isCaptchaRequired(req,token)){
//			if (user == null) {
//				return loginFailure(token,new UnknownAccountException(),request,response,user);
//			}
//			if (!user.isStatus()) {
//				return loginFailure(token,new UnknownAccountException(),request,response,user);
//			}
//			try {
//				Subject subject = getSubject(request, response);
//				subject.login(token);
//				return loginSuccess(token,subject,request,response,user);
//			} catch (AuthenticationException e) {
//				LoggerUtils.error(getClass(), 117, e);
//				e.printStackTrace();
//				return loginFailure(token,e,request,response,user);
//			}finally{
//				token.clear();
//			}
//		}else {
//			if (token.getCaptcha() == null || "".equals(token.getCaptcha())) {
//				return loginFailure(token,new CaptchaRequiredException(),request,response,user);
//			}else {
//				return loginFailure(token,new CaptchaErrorException(),request,response,user);
//			}
//		}
//	}
//	
//	/**
//	 * 所有请求都会经过的方法
//	 */
//	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception{
//		LoggerUtils.error(getClass(), 119, "onAccessDenied");
//		HttpServletRequest rq = (HttpServletRequest) request;
//		HttpServletResponse rp = (HttpServletResponse) response;
//		if (isLoginRequest(request, response)) {
//			if (isLoginSubmission(request, response)) {
//				return executeLogin(request, response);
//			}else {
//				return true;
//			}
//		}else {
//			if (!"XMLHttpRequest".equalsIgnoreCase((rq).getHeader("X-Requested-With"))) {
//				saveRequestAndRedirectToLogin(request, response);
//			}else {
//				rp.setCharacterEncoding("UTF-8");
//				PrintWriter out = rp.getWriter();
//				out.flush();
//				out.close();
//			}
//			return false;
//		}
//	}
//	
//	/*
//	 * 主要是针对登入成功的处理方法，对于请求头是ajax的之间返回JSON字符串
//	 */
//	protected boolean loginSuccess(AuthenticationToken token,Subject subject,ServletRequest request,
//			ServletResponse response,SysUser user) throws Exception{
//		LoggerUtils.error(getClass(), 162, "loginSuccess");
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		String username = (String)token.getPrincipal();
//		String ip = RequestUtils.getIpAddr(req);
//		userService.updateLoginInfo(user.getId(),ip);
//		//管理登录
//		logService.loginSuccess(req,"username="+username);
//		user.setLoginErrorCount(0);
//		user.setLoginCount(user.getLoginCount());
//		userService.merge(user);
//		removeCookieErrorRemaining(req,res);
//		subject.getSession().setAttribute(SESSION_ADMIN_SUCCESS_URL, getSuccessUrl());
//		subject.getSession().setAttribute(SESSION_ADMIN_LOGIN_URL, getLoginUrl());
//		if (!"XMLHttpRequest".equalsIgnoreCase((req).getHeader("X-Requested-With"))) {
//			issueSuccessRedirect(request, response);
//			return super.onLoginSuccess(token, subject, request, response);
//		}else {
//			res.setCharacterEncoding("UTF-8");
//			res.setContentType("text/html;charset=utf-8");
//			PrintWriter out = res.getWriter();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("success", true);
//			map.put("message", "登入成功");
//			map.put("url", req.getContextPath()+getSuccessUrl());
//			out.print(FastJSONHelper.serialize(map, ""));
//			out.flush();
//			out.close();
//		}
//		return false;
//	
//	}
//	
//	/*
//	 * 处理登入失败的方法
//	 */
//	protected boolean loginFailure(AuthenticationToken token,AuthenticationException e,ServletRequest request,
//			ServletResponse response,SysUser user){
//		LoggerUtils.error(getClass(), 197, "loginFailure");
//		String username = (String)token.getPrincipal();
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		if (user != null) {
//			writeCookieErrorRemaining(user.getLoginErrorCount(),req,res);
//			logService.loginFailure(req,"username="+username);
//			user.setLoginErrorCount(user.getLoginErrorCount() + 1);
//			userService.merge(user);
//		}
//		LoggerUtils.error(getClass(), 205, e);
//		
//		if (!"XMLHttpRequest".equalsIgnoreCase((req).getHeader("X-Requested-With"))) {
//			setFailureAttribute(request, e);
//			LoggerUtils.error(getClass(), 209, "不是ajax请求");
//			return super.onLoginFailure(token, e, request, response);
//		}try {
//			LoggerUtils.error(getClass(), 213, "ajax请求");
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=utf-8");
//			PrintWriter out = response.getWriter();
//			String message = e.getClass().getSimpleName();
//			LoggerUtils.error(getClass(), 215, message);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("success", false);
//			String msg = null;
//			if ("IncorrectCredentialsException".equals(message)) {
//				msg = "密码错误";
//			}else if ("UnkonwnAccountException".equals(message)) {
//				msg = "账号不存在";
//			}
//			else if ("LockedAccountException".equals(message)) {
//				msg = "账号被锁定";
//			}
//			else if ("ExcessiveAttemptsException".equals(message)) {
//				msg = "登录失败次数过多";
//			}
//			else if ("DisabledAccountException".equals(message)) {
//				msg = "账号被禁用";
//			}
//			else if ("IllegalArgumentException".equals(message)) {
//				msg = "验证失败";
//			}
//			else if ("CaptchaRequiredException".equals(message)) {
//				msg = "请输入验证码";
//			}
//			else if ("CaptchaErrorException".equals(message)) {
//				msg = "验证码错误";
//			}else{
//				msg = "登录验证失败";
//			}
//			map.put("message", msg);
//			out.print(FastJSONHelper.serialize(map, ""));
//			out.flush();
//			out.close();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		} 
//		return false;
//	
//	}
//	
//	protected boolean isCaptchaRequired(HttpServletRequest request,CaptchaUsernamePasswordToken token){
//		String captcha = RequestUtils.getQueryParam(request,com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//		LoggerUtils.error(getClass(), 253, "isCaptchaRequired");
//		LoggerUtils.error(getClass(), 254, captcha);
//		if (StringUtils.isNotBlank(captcha) && captcha.equals(token.getCaptcha())) {
//			return true;
//		}
//		return true;
//	}
//	
//	private void writeCookieErrorRemaining(Integer userErrorRemaining,HttpServletRequest request,
//			HttpServletResponse response){
//		LoggerUtils.error(getClass(), 265, "writeCookieErrorRemaining");
//		//所有访问页面都需要写一个cookie，这样可以判断已经登录了几次
//		Integer errorRemaing = getCookieErrorRemaining(request,response);
//		SysConfig configLogin = configService.get(1l);
//		Integer errorInterval = configLogin == null ? 0 :configLogin.getLoginError();
//		if (userErrorRemaining != null && (errorRemaing == null ||userErrorRemaining < errorRemaing)) {
//			errorRemaing = userErrorRemaining;
//		}
//		int maxErrorTimes = configLogin == null ? 0 :configLogin.getLoginErrorTime();
//		if (errorRemaing == null || errorRemaing > maxErrorTimes) {
//			errorRemaing = maxErrorTimes;
//		}else if (errorRemaing <= 0) {
//			errorRemaing = 0;
//		}else {
//			errorRemaing--;
//		}
//		CookieUtils.addCookie(request,response,COOKIE_ERROR_REMAINING,errorRemaing.toString(),errorInterval * 60,null);
//		
//	}
//	
//	private void removeCookieErrorRemaining(HttpServletRequest request,HttpServletResponse response){
//		LoggerUtils.error(getClass(), 290, "removeCookieErrorRemaining");
//		CookieUtils.cancleCookie(request,response,COOKIE_ERROR_REMAINING,null);
//	}
//	
//	private Integer getCookieErrorRemaining(HttpServletRequest request,HttpServletResponse response){
//		LoggerUtils.error(getClass(), 297, "getCookieErrorRemaining");
//		Cookie cookie = CookieUtils.getCookie(request,COOKIE_ERROR_REMAINING);
//		if (cookie != null) {
//			String value = cookie.getValue();
//			if (NumberUtils.isDigits(value)) {
//				return Integer.parseInt(value);
//			}
//		}
//		return null;
//	}
	
}
