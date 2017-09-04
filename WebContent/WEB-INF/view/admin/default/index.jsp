<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<base href="<%=basePath%>"/>
		<title>${site.name}-${site.versions}</title>	
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<link href="<%=basePath%>s/assets/css/pace.css" rel="stylesheet" />
		<script src="<%=basePath%>s/assets/js/pace.js"></script>
		
		<!-- basic styles -->
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/font-awesome.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/bootstrap-table/bootstrap-table.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/css/bootstrap-datepicker3.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/css/bootstrap-timepicker.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/css/daterangepicker.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/css/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/chosen.css" />
		<link rel="stylesheet"
			href="<%=basePath%>s/assets/css/chrome_scroll.css" />
		<!-- text fonts -->
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/ace-fonts.css" />
		
		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/ace.css"
			class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/style.css" />
		<link rel="stylesheet" href="<%=basePath%>s/assets/css/clockface.css" />		
		<!-- ace settings handler -->
		<script src="<%=basePath%>s/assets/js/ace-extra.js"></script>
			
		<link href="<%=basePath%>s/favicon-ico" rel="icon"/>
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							蜻蜓v1.0
						</small>
					</a>
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-tasks"></i>
								<span class="badge badge-grey">4</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-check"></i>
									还有4个任务未完成
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar">
										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">Software Update</span>
													<span class="pull-right">65%</span>
												</div>

												<div class="progress progress-mini">
													<div style="width:65%" class="progress-bar"></div>
												</div>
											</a>
										</li>

										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">Hardware Upgrade</span>
													<span class="pull-right">35%</span>
												</div>

												<div class="progress progress-mini">
													<div style="width:35%" class="progress-bar progress-bar-danger"></div>
												</div>
											</a>
										</li>

										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">Unit Testing</span>
													<span class="pull-right">15%</span>
												</div>

												<div class="progress progress-mini">
													<div style="width:15%" class="progress-bar progress-bar-warning"></div>
												</div>
											</a>
										</li>

										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">Bug Fixes</span>
													<span class="pull-right">90%</span>
												</div>

												<div class="progress progress-mini progress-striped active">
													<div style="width:90%" class="progress-bar progress-bar-success"></div>
												</div>
											</a>
										</li>
									</ul>
								</li>

								<li class="dropdown-footer">
									<a href="#">
										See tasks with details
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important">8</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-exclamation-triangle"></i>
									8 条通知
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar navbar-pink">
										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
														新评论
													</span>
													<span class="pull-right badge badge-info">+12</span>
												</div>
											</a>
										</li>

										<li>
											<a href="#">
												<i class="btn btn-xs btn-primary fa fa-user"></i>
												切为编辑登录 ... ...
											</a>
										</li>

										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
														新订单
													</span>
													<span class="pull-right badge badge-success">+8</span>
												</div>
											</a>
										</li>

										<li>
											<a href="#">
												<div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
														粉丝
													</span>
													<span class="pull-right badge badge-info">+11</span>
												</div>
											</a>
										</li>
									</ul>
								</li>

								<li class="dropdown-footer">
									<a href="#">
										查看所有通知
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
								<span class="badge badge-success">5</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-envelope-o"></i>
									5条消息
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar">
										<li>
											<a href="#" class="clearfix">
												<img src="<%=basePath%>s/assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Alex:</span>
														Ciao sociis natoque penatibus et auctor ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>1分钟以前</span>
													</span>
												</span>
											</a>
										</li>

										<li>
											<a href="#" class="clearfix">
												<img src="<%=basePath%>s/assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Susan:</span>
														Vestibulum id ligula porta felis euismod ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>20分钟以前</span>
													</span>
												</span>
											</a>
										</li>

										<li>
											<a href="#" class="clearfix">
												<img src="<%=basePath%>s/assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Bob:</span>
														Nullam quis risus eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>3:15 pm</span>
													</span>
												</span>
											</a>
										</li>

										<li>
											<a href="#" class="clearfix">
												<img src="<%=basePath%>s/assets/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Kate:</span>
														Ciao sociis natoque eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>1:33 pm</span>
													</span>
												</span>
											</a>
										</li>

										<li>
											<a href="#" class="clearfix">
												<img src="<%=basePath%>s/assets/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Fred:</span>
														Vestibulum id penatibus et auctor  ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>10:09 am</span>
													</span>
												</span>
											</a>
										</li>
									</ul>
								</li>

								<li class="dropdown-footer">
									<a href="#page/inbox">
										查看所有消息
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="<%=basePath%>s/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎!用户</small>
									${user.username}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										设置
									</a>
								</li>

								<li>
									<a href="#page/profile">
										<i class="ace-icon fa fa-user"></i>
										个人资料
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${basePath}logout">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar responsive" >
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>
				
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->

				<ul class="nav nav-list">
					<li class="active">
						<a href="index.html">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text"> 控制台 </span>
						</a>

						<b class="arrow"></b>
					</li>
					
					<c:forEach var="menu" items="${menus}">
						<c:if test="${menu.children !=null }">
							<li>
								<a href="#" class="dropdown-toggle">
									<i class="menu-icon fa fa-<c:out value='${menu.iconCls}' />"></i>
									<span class="menu-text"><c:out value="${menu.name}" /></span>
									<b class="arrow fa fa-angle-down"></b>
								</a>
								<b class="arrow"></b>
								<ul class="submenu">
									<c:forEach var="m" items="${menu.children}">
									<li>
										<a data-url="<c:out value='${m.url}'/>" href="${mainPath}#<c:out value='${m.url}'/>">
											<i class="fa fa-<c:out value='${m.iconCls}'/>"></i>
											<c:out value="${m.name}"/>
										</a>
										<b class="arrow"></b>
									</li>
									</c:forEach>
								</ul>
							</li>
						</c:if>
							
						<c:if test="${menu.children ==null }">
							<li>
								<a data-url="<c:out value='${menu.url}'/>" href="${mainPath}#<c:out value='${menu.url}'/>">
											<i class="fa fa-<c:out value='${menu.iconCls}'/>"></i>
											<span class="menu-text"><c:out value="${menu.name}"/></span>			
								</a>
								<b class="arrow"></b>
							</li>
						</c:if>
					</c:forEach>			
				</ul><!-- /.nav-list -->
				
				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="${mainPath}">首页</a>
							</li>
						</ul><!-- /.breadcrumb -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- #section:settings.box -->
						<div class="ace-settings-container" id="ace-settings-container">
							<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
								<i class="ace-icon fa fa-cog bigger-130"></i>
							</div>

							<div class="ace-settings-box clearfix" id="ace-settings-box">
								<div class="pull-left width-50">
									<!-- #section:settings.skins -->
									<div class="ace-settings-item">
										<div class="pull-left">
											<select id="skin-colorpicker" class="hide">
												<option data-skin="no-skin" value="#438EB9">#438EB9</option>
												<option data-skin="skin-1" value="#222A2D">#222A2D</option>
												<option data-skin="skin-2" value="#C6487E">#C6487E</option>
												<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
											</select>
										</div>
										<span>&nbsp; 选择皮肤</span>
									</div>

									<!-- /section:settings.skins -->

									<!-- #section:settings.navbar -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
										<label class="lbl" for="ace-settings-navbar">固定导航栏</label>
									</div>

									<!-- /section:settings.navbar -->

									<!-- #section:settings.sidebar -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
										<label class="lbl" for="ace-settings-sidebar">固定侧边栏</label>
									</div>

									<!-- /section:settings.sidebar -->

									<!-- #section:settings.breadcrumbs -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
										<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑导航</label>
									</div>

									<!-- /section:settings.breadcrumbs -->
	
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
										<label class="lbl" for="ace-settings-add-container">切换到窄屏</label>
									</div>

								</div><!-- /.pull-left -->

								<div class="pull-left width-50">
									<!-- #section:basics/sidebar.options -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
										<label class="lbl" for="ace-settings-hover">鼠标滑过显示子菜单</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
										<label class="lbl" for="ace-settings-compact">紧凑侧边栏</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
										<label class="lbl" for="ace-settings-highlight">菜单项突出</label>
									</div>

									<!-- /section:basics/sidebar.options -->
								</div><!-- /.pull-left -->
							</div><!-- /.ace-settings-box -->
						</div><!-- /.ace-settings-container -->
						
						<div class="page-content-area" data-ajax-content="true">
							<!-- ajax content goes here -->
						</div>
						
						<!-- AJAX AUTH STATUS-->
						<div id="requestStatus" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="false" data-keyboard="false">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">x</button>
										<div class="span12 caption">
											<i class="glyphicon glyphicon-alert" aria-hidden="true"></i>
											<font class="title">提示信息</font>
										</div>
									</div>
									<div class="modal-body" style="min-height:180px">
										<div class="row-fluid">
											<div class="span12">
												<div class="control-group">
													<div class="controls">
														<span class="portlet-body text"></span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button class="btn btn-sm btn-danger pull-right" data-dismiss="modal" id="ok">确定</button>
									</div>
								</div>
							</div>
						</div>
						<!-- /section:settings.box -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">蜻蜓</span>
							Application &copy; 2014-2015
						</span>

						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
			
	</body>
</html>

<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='<%=basePath%>s/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->

<!--[if IE]> -->
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=basePath%>s/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>s/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>

<script src="<%=basePath%>s/assets/js/bootstrap.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/moment.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/bootstrap-datepicker.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/bootstrap-datetimepicker.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/bootstrap-timepicker.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/daterangepicker.js"></script>
<script src="<%=basePath%>s/assets/js/date-time/locales/moment-locale-zh-cn.js"></script> 

<script src="<%=basePath%>s/assets/js/chosen.jquery.js"></script>
<script src="<%=basePath%>s/assets/js/jquery-ui.js"></script>

<script src="<%=basePath%>s/assets/js/autosize.js"></script>
<script src="<%=basePath%>s/assets/js/bootstrap-tag.js"></script>
<script src="<%=basePath%>s/assets/js/clockface.js"></script>

<!-- ace scripts -->
<script src="<%=basePath%>s/assets/js/ace/elements.scroller.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.colorpicker.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.fileinput.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.typeahead.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.wysiwyg.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.spinner.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.treeview.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.wizard.js"></script>
<script src="<%=basePath%>s/assets/js/ace/elements.aside.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.ajax-content.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.touch-drag.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.sidebar.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.submenu-hover.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.widget-box.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.settings.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.settings-rtl.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.settings-skin.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.widget-on-reload.js"></script>
<script src="<%=basePath%>s/assets/js/ace/ace.searchbox-autocomplete.js"></script>

<%@include file="../commons/include.jsp" %>