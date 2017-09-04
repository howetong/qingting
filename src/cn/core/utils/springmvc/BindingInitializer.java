package cn.core.utils.springmvc;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import cn.core.utils.LoggerUtils;

public class BindingInitializer implements WebBindingInitializer {
	/**
	 * 初始化数据绑定
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {
		
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
		String path = request.getContextPath();
		LoggerUtils.info(getClass(), 26, path);
		Principal user = request.getUserPrincipal();
		LoggerUtils.info(getClass(), 28, user);
		Iterator<String> names = request.getParameterNames();
		while (names.hasNext()) {
			String type = names.next();
			String p = request.getParameter(type);
			LoggerUtils.info(getClass(), 31, p);
		}
	}
}
