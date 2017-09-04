<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp" %>
<link rel="stylesheet" href="<%=basePath%>s/assets/ztree/css/bootstrap/zTreeStyle.css"/>
<title>用户列表 - 用户管理</title>
<div class="row">
	<div class="col-xs-12">
		<h3 class="header smaller lighter no-margin-bottom">用户管理</h3>
		<div id="toolbar" class=".tableTools-action"></div>
		<table id="table"  class="table table-striped table-hover"></table>
	</div>
</div>
<!--
fade:淡入淡出效果

-->
<div id="edit" class="modal fade in bs-example-modal-lg"  role="dialog" tabindex="-1"  aria-labelledby="title">
	<div class="modal-dialog  modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				
				<div class="span12 caption">
					<i class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></i>
					<font id="title" class="title">新建用户</font>
				</div>
			</div>
			<div class="modal-body" style="overflow: auto;min-height:380px">
				<div class="row-fluid">
					<!-- BEGIN FORM-->
					<form id="save" action="#" class="form-horizontal form-bordered form-row-stripped" role="form" novalidate="novalidate" cmd="save">
					
					</form>
					<!-- END FORM-->  
				</div>
			</div>
			<div class="modal-footer">
				<button id="but-close" class="btn btn-sm btn-danger">
					<i class="ace-icon fa fa-times"></i>
					关闭
				</button>
				<button id="but-save" class="btn btn-sm btn-success">
					<i class="ace-icon fa fa-check"></i>
					保存
				</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var scripts = [null,
		"<%=basePath%>s/assets/bootstrap-table/bootstrap-table.js",
		"<%=basePath%>s/assets/bootstrap-table/locale/bootstrap-table-zh-CN.js",
		"<%=basePath%>s/assets/bootstrap-table/extensions/key-events/bootstrap-table-key-events.js",
		"<%=basePath%>s/assets/ztree/js/jquery.ztree.all.min.js",
        "<%=basePath%>s/assets/js/jquery.input-ip-address-control-1.0.min.js",
        "<%=basePath%>s/assets/js/jquery.inputmask.bundle.min.js",
        "<%=basePath%>s/assets/js/jquery.inputlimiter.1.3.1.js",
        "<%=basePath%>s/assets/js/jquery.validate.js",
       	null]; 
   	$('.page-content-area').ace_ajax('loadScripts',scripts,function(){
   		jQuery(function($){
   			var no = 1;
   			
   			/**
   			表格与表单字段
   			属性
   			field:后台对象属性
   			type:表单字段类型，如文本框，下拉列表等
   			size:表单允许输入的最长字数
   			help:表单字段的介绍与帮助信息
   			required:表单是否必须填写
   			list:下拉列表，单选按钮选项列表：属性值：显示名称，如list：{"true":"正常","false":"禁用"}
   			equalsTo 密码字段重复性判断
   			*/
   			var columns = $.bootstrapTable.config.columns = [
              {
	    	  	"field":"id","class":"no","fieldType":"hidden","form":true,"sortable":false,"formatter" : function(value,row)
    		  	{
	    	  		var content = '<label class="pos-rel"><input type="checkbox" class="ace" name="id" value="'+ value +'"><span class="lbl"></span></label>';
					return content;
    	      	}
		      },
		      {"field":"username","title":"用户名","class":"fieldname","fieldType":"text","form":true,"required":true},
		      { "field":"name","title":"姓名","class":"fieldname","fieldType":"text","form":true,"size":16},
		      {"field":"password","title":"密码","class":"user","fieldType":"password","visible":false,"form":true,"required":true,
		    	  "list":{"equalTo":true},"formatter":function(value,row){
				    	 return "***";
				     }
			  },
			  { "field":"roles","title":"角色","class":"fieldname","fieldType":"zTree","form":true,"required":false,
				  "list":{"ajaxUrl":XY.Util.app.contentPath+"admin/role/selectlist"},
				  "formatter":function(value,row){
					  if(row.allRole === true){
							return "所有角色";
						}else if(value && value.length){
							return value.length;
						}
						return "-";
				  }
			  },
		      {
		      	 "field":"createTime","title":"创建时间","class":"time","fieldType":"dataTime","form":false,"formatter":function(value,row){
		      		if(value){
		      			return XY.Util.dateFormat('yyy-MM-dd HH:mm:ss',value);
		      		}
		      		return "-";
		      	 }   
		      },
		     
			  {
				 "field":"status","title":"状态","class":"status","fieldType":"select","list":{"data":[{"id":1,"name":"正常"},{"id":0,"name":"禁用"}]},"form":true,"formatter":function(value,row){
					 var content = "-";
					 if(value == 1 || value == true){
						 content = '<span class="label label-success middle"><i class="glyphicon glyphicon-ok-circle"></i></span>';
					 }else{
						 content = '<span class="label label-warning middle"><i class="glyphicon glyphicon-ban-circle"></i></span>';
					 }
					 return content;
				 }
			  },
			  {
				 "field":"lastLoginIp","title":"登录ip","class":"ip","fieldType":"ip","form":false,"formatter":function(value,row){
			    	 if(value){
			    		 return value;
			    	 }
			    	 return ":::";
				  } 
			  },
			  {
				 "field":"lastLoginTime","title":"上次登录时间","class":"time","fieldType":"dataTime","form":false,"formatter":function(value,row){
			    	 if(value){
			    		 return XY.Util.dateFormat('yyy-MM-dd HH:mm:ss',value);
			    	 }
			    	 return "-";
				  } 
			  },
			  {
				 "field":"loginCount","title":"登录次数","class":"loginCount","fieldType":"number","form":false,"formatter":function(value,row){
			    	 if(value){
			    		 return value;
			    	 }
			    	 return "0";
				  } 
			  },
			  {
				 "field":"updateTime","title":"修改时间","class":"time","fieldType":"dataTime","form":false,"formatter":function(value,row){
			    	 if(value){
			    		 return XY.Util.dateFormat('yyy-MM-dd HH:mm:ss',value);
			    	 }
			    	 return "-";
				  } 
			  },
			  {
				 "field":"description","title":"描述","class":"content","fieldType":"textarea","visible": false,"form":true,size:150
			  },
			  {
				  "sortable":false,"class":"action","title":"操作","form":false,"formatter":function(value,row){
					  var editA = '<button  id="act_edit" class="btn btn-xs btn-info" did="'+row.id+'" title="编辑"  data-toggle="tooltip"><i class="ace-icon fa fa-pencil bigger-120"></i></button>';
					  var delA = '<button id="act_del" class="btn btn-xs btn-danger" did="'+row.id+'" title="删除"  data-toggle="tooltip"><i class="ace-icon fa fa-trash-o bigger-120"></i></button>';
					  return '<div class="hidden-sm hidden-xs btn-group">'+editA+delA+'</div>';
				  }
			  }
   			];
   			var menUrl = "admin/user";
			/**
			表格获取数据的ajax数据源
			*/
			$.bootstrapTable.config.url = $.bootstrapTable.contentPath + menUrl;
   			/**
   			*搜索框
   			*/
   			/* $.bootstrapTable.config.searching = false; */
   			/**
			绘制完成后的回调,处理操作事件
			*/
			$.bootstrapTable.config.onPostBody = function(e,data){
				
				$('button[id="act_edit"]').click(function(){
					$.bootstrapTable.edit('#edit',this.attributes.did.value,$.bootstrapTable.contentPath + menUrl + '/edit',$.bootstrapTable.config.columns,'编辑','edit');
				});
				$('button[id="act_del"]').on('click',function(){
					$.bootstrapTable.del('#requestStatus',this.attributes.did.value,$.bootstrapTable.contentPath + menUrl + '/delete');
				});
				
				$('a[id="act_dels"]').on('click',function(){
					var ids = $('td input[type="checkbox"][name="id"]:checked');
					var valids = [];
					for(var i = 0;i < ids.length || 0 ; i ++){
						var val = ids[i].value;
						valids.push(val)
					}
					$.bootstrapTable.del('#requestStatus',valids,$.bootstrapTable.contentPath+'admin/user/delete');
				});
			}
   			/**
   			*动态向action内添加操作元素
   			*/
   			var action = $('#toolbar');
			var addBut = $('<a class="dt-button buttons-collection btn btn-white btn-info btn-bold" data-toggle="tooltip" data-placement="bottom" title="添加"><i class="ace-icon glyphicon glyphicon-plus bigger-110 blue"></i></a>');
			var removeBut = $('<a id="act_dels" class="dt-button buttons-collection btn btn-white btn-info btn-bold" data-toggle="tooltip" data-placement="bottom" title="删除选中"><i class="ace-icon glyphicon glyphicon-remove bigger-110 orange"></i></a>');
			
			var delBut = $('<a class="dt-button buttons-collection btn btn-white btn-info btn-bold" data-toggle="tooltip" data-placement="bottom" title="彻底删除"><i class="ace-icon glyphicon glyphicon-trash bigger-110 red"></i></a>');
			action.append(addBut);
			action.append(removeBut);
			action.append(delBut);
			$(addBut).click(function(){
				$.bootstrapTable.edit('#edit',null,$.bootstrapTable.contentPath+'admin/user/save',$.bootstrapTable.config.columns,'新建用户');
			});
			$.bootstrapTable.table = $('#table').bootstrapTable($.bootstrapTable.config);
			
   			
   			/**
   			表单保存路径
   			*/
   			FormSamples.init({saveUrl :$.bootstrapTable.contentPath + "admin/user/save"});
   		});
   	});
</script>

	
