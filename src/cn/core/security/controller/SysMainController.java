package cn.core.security.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.core.controller.CRUDController;
import cn.core.domain.BaseBean;
import cn.core.domain.ParentChildrenBean;
import cn.core.security.domain.SysMenu;
import cn.core.security.service.ISysMenuService;
import cn.core.utils.LoggerUtils;

/**
 * @function 后台首页相关界面数据
 * @author howeTong
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/admin")
public class SysMainController extends CRUDController<SysMenu>{
	@Resource
	protected ISysMenuService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView doIndex(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv = new ModelAndView();
		Map<String, String> sortedCondition = new HashMap<>();
		sortedCondition.put("sequence", "ASC");
		LoggerUtils.error(getClass(), 41, "admin...");
		/*LoggerUtils.error(getClass(), 42, getPageView());*/
		List<SysMenu> rootMenu = this.service.queryByProperties(new String[]{ParentChildrenBean.PARENT_FIELD,BaseBean.STATUS_FIELD}, 
				new Object[]{null,false},sortedCondition,SysMenu.class);
		/*writeJSON(response, rootMenu);
		return null;*/
		mv.addObject("menus",rootMenu);
		/*mv.addObject("user",getCurrentSysUser());*/
		mv.addObject("mainPath",getCurrentUrl());
		mv.addObject("loginPath",getLoginUrl());
		mv.addObject("site",this.getSiteConfig());
		mv.setViewName(getPageDirectoryView() + "index");
		return mv;
	}
	
	@RequestMapping(value="/tree",method=RequestMethod.GET)
	public void doEdit(String id,HttpServletRequest request,HttpServletResponse response)
		throws IOException{
		List<SysMenu> menus = null;
		Map<String, String> sortCondition = new HashMap<>();
		sortCondition.put("sequence", "ASC");
		if (id != null && isNumberNot0(id)) {
			menus = this.service.queryByProperties(new String[]{ParentChildrenBean.PARENT_FIELD,BaseBean.STATUS_FIELD}, 
				new Object[]{new SysMenu(new Long(id)),false},SysMenu.class);
		}else {
			menus = this.service.queryByProperties(new String[]{ParentChildrenBean.PARENT_FIELD,BaseBean.STATUS_FIELD}, 
				new Object[]{null,false},sortCondition,SysMenu.class);
		}
		writeJSON(response, menus);
	}
}
