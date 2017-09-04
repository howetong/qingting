package cn.core.security.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import cn.core.domain.support.PageView;
import cn.core.domain.support.QueryResult;
import cn.core.security.domain.SysMenu;
import cn.core.security.service.ISysMenuService;


@SuppressWarnings(value="all")
@Controller
@RequestMapping("admin/menu")
public class SysMenuController extends CRUDController<SysMenu>{
	@Resource
	private ISysMenuService service;
}
