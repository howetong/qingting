<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="<%=basePath%>s/assets/js/commons/Th.js"></script>
<script type="text/javascript" src="<%=basePath%>s/assets/js/commons/index.js"></script>

<script>
	var app = XY.Util.app = new App(); 
	app.init();
	app.contentPath = ""+"<%=basePath%>";
	app.mainPath = ""+"${mainPath}";
	app.loginPath = ""+"${loginPath}";
	app.rootPath = ""+"${basePath}";
</script>
<script type="text/javascript" src="<%=basePath%>s/assets/js/commons/extend.js"></script>
<script type="text/javascript" src="<%=basePath%>s/assets/js/commons/dtExtend.js"></script>
<script type="text/javascript" src="<%=basePath%>s/assets/js/commons/FormSample.js"></script>