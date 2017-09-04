/**
 * config
 */
(function(){
	var app = XY.Util.app;
	$.bootstrapTable = {
		/**
		上下文路径
		*/
		contentPath : app.contentPath,
		table : null,
		config : {
			"columns": [
				{ "field":"id","sWidth":40,"class":"no","fieldType":"hidden","form":true,"sortable": true,"formatter":function(value,row){
					var content = '<label class="pos-rel"><input type="checkbox" class="ace" name="id" value="'+ row.id +'"><span class="lbl"></span></label>';
					return content;
				}}
			],
			"toggle":"table",
			//"showPaginationSwitch":true,//收缩与展开
			"keyEvents":true,
			/**  参数设置开始   */
			"url" : "",//请求后台的url
			"method" : "post",//请求方式
			"striped" : true,//是否显示行间隔色
			"cache" : false,//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			"pagination" : true,//是否显示分页
			"sortable" : false,//是否启用排序
			"sortName" : 'id',
	        "sortOrder": "asc",//排序方式
	        "queryParams" :  function(params){
				params.rows = params.limit;
				params.page = params.offset / params.limit + 1;
				return params;
			},//传递参数
			"sidePagination" : "server",//分页方式：client客户端分页，server服务器端分页
			"pageNumber" : 1,//初始化加载第一页，默认第一页
			"pageSize" : 10, //每页的记录行数（*）
			"pageList" : [5, 10, 20, 50, 100, 200],//可供选择的每页的行数（*）
			/**
			 * 由于bootstrap的css和ace的css冲突，故不采用表格自带的工具栏和相关工具按钮
			 */
			"toolbar" : "#toolbar",//工具按钮用哪个容器
			"search" : true,//服务器端分页才有效 
			"showColumns" : true,//是否显示所有的列      
			"showRefresh" : true,//开启刷新功能
			"clickToSelect" : true,//是否启用点击选中行
			"minimumCountColumns" : 2,//最少允许的列数     
			//"height" : 680,//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度      
			"uniqueId" : "id",//每一行的唯一标识，一般为主键列
			//"showToggle":true,//是否显示详细视图和列表视图的切换按钮
			"detailView": true,//是否显示父子表 
			"cardView" : false, //是否显示详细视图
	        "detailView" : false, //是否显示父子表
	        "buttonsClass" : "white",
	        "contentType" : "application/x-www-form-urlencoded",
			"icons": {
				paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
				paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
				refresh: 'glyphicon-refresh icon-refresh',
				toggle: 'glyphicon-list-alt icon-list-alt',
				columns: 'glyphicon-th icon-th',
				detailOpen: 'glyphicon-plus icon-plus',
				detailClose: 'glyphicon-minus icon-minus'
			},
			/**
			绘制完成后的回调
			*/
			"onPostBody":function(e,row){
				var tablecof = $.bootstrapTable.config;
				var tableUrl = $.bootstrapTable.contentPath+tablecof.url;
				var tableColumns = tablecof.columns;
				var id = this.attributes.did.value;
				$('a[id="act_edit"]').click(function(){
					$.bootstrapTable.edit('#edit',id,tableUrl,tableColumns,'编辑');
				});
				$('a[id="act_del"]').on('click',function(){
					$.bootstrapTable.del('#requestStatus',id,tableUrl,tableColumns,'删除');
				});
			}
		},
		/**
		删除
		*/
		del : function(modalId,id,url){
			$.ajax({
					"dataType":"json",
					"type":"post",
					"url":url,
					"data":'ids='+(id || 0),
					"success":function(data){
						$.bootstrapTable.table.bootstrapTable("refresh");
					}
				});
		},

		/**
		*编辑函数
		*madalId为编辑框id，id为记录id，url为后台编辑逻辑处理函数url，
		*columns为前端表格数据，title为标题，act为动作（new | edit）
		*/
		edit : function(modalId,id,url,columns,title,act){
			var options = {
				backdrop : false,
				keyboard : false
			}
			var self = {
				saveUrl:url,
				act : act||'new',
				data : [],
				table : this.table,
				columns:columns
			};
			var loadForm = function(self){
				FormSamples.Load(self);
				$('.title',$(modalId)).html((self.act === "new" ? title.replace('编辑','新建') : title));
				$('form',$(modalId)).attr('cmd',self.act);
				$(modalId).modal(options);
			}
			if(id > 0){
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":url,
					"data":'id='+(id || 0),
					"success":function(data){
						if(data){
							for(var i =0; i < columns.length;i ++){
								if(!columns[i] || !columns[i].field)
									continue;
								columns[i].value = data[columns[i].field];
							}
							self.data = data;
							loadForm(self);
						}else{
							$('.text',$('#requestStatus')).html('数据获取失败!');
							$('#ok',$('#requestStatus')).attr('did','load');
							$("#requestStatus").modal(options);	
						}
					}
				});
			}else{
				for(var i =0; i < columns.length;i ++){
					if(!columns[i] || !columns[i].field)
						continue;
					columns[i].value = columns[i].defVal;
				}
				loadForm(self);
			}
		},
		/**
		*菜单类型样式
		*/
		menuType : {
			"type-1" : function(text){
				return '<span class="label  label-inverse btn-xs">'+text+'</span>'
			},
			"type0" : function(text){
				return '<span class="label  label-inverse btn-xs">'+text+'</span>'
			},
			"type1" : function(text){
				return '<span class="label  label-success btn-xs">'+text+'</span>'
			},
			"type2" : function(text){
				return '<span class="label  label-info btn-xs">'+text+'</span>'
			},
		},
	buttionInit : function(){	
			setTimeout(function() {
				$($('.bootstrap-table')).find('.dt-button').each(function(a,b,c) {
					var div = $(this).find(' > div').first();
					if(div.length == 1) {
						if(div.parent().text() && div.parent().text() != "") this.title = div.parent().text();
						div.tooltip({container: 'body', title: div.parent().text()});
					}else {
						if($(this).text() && $(this).text() != "")this.title = $(this).text();
						$(this).tooltip({container: 'body', title: $(this).text()});
					}
				});
			}, 500);
		}
	}
})()