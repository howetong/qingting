var FormSamples = function () {
	// id,className,text
	var domdiv = XY.Util.DOM.createDivRelative;
	var domlabel = XY.Util.DOM.createLabel;
	var domspan = XY.Util.DOM.createSpan;
	// id,type,value,className,placeholder
	var dominput = XY.Util.DOM.createInput;
	var domtextarea = XY.Util.DOM.createTextarea;
	// id,type,value,className
	var dombutton = XY.Util.DOM.createButton;
	// id,value,className
	var domselect = XY.Util.DOM.createSelect;
	// id,className,text
	var domi = XY.Util.DOM.createI;
	// id,href,className,text
	var doma = XY.Util.DOM.createA;
	var domtable = XY.Util.DOM.createTable;
	var domthead = XY.Util.DOM.createThead;
	var domtr = XY.Util.DOM.createTr; 
	var requiredFn = function(required){
		return (required === true ) ? "<font style='color:red;'>*必填</font>" : (required !== false) ? required : "";
	}
	var mainFormFieldIdTag;
	var dialogClickFun = function(id,title,mainFormFieldId,columns,width,height){
		$(id).click(function(e) {
			e.preventDefault();
			// 点击保存按钮
			$('#save',$('#exp')).click(function(e){
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":FormSamples.saveUrl,
					"data":"cmd="+$('form',$('#editBox')).attr('cmd')+"&"+$('#exp').serialize(),
					"success":function(data){
					}
				});
			});
			$('a[id="formAct_edit"]').click(function(){
				$.userExtend.DataTables.edit('#editBox',this.attributes.did.value,$.userExtend.DataTables.contentPath+'admin/user/edit',$.userExtend.DataTables.config.aoColumns,'编辑用户');
				});
			$('a[id="formAct_del"]').on('click',function(){
				$.userExtend.DataTables.del('#requestStatus',this.attributes.did.value,$.userExtend.DataTables.contentPath+'admin/user/delete',$.userExtend.DataTables.config.aoColumns,'用户编辑');
			});
			var dialog = $( "#exp" ).removeClass('hide').dialog({
				// modal: true,
				width:width || "40%",
				minWidth:320,
				height:height || 360,
				title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i> "+title+"</h4></div>",
				title_html: true,
				buttons: [ 
					{
						text: "关闭",
						"class" : "btn btn-minier",
						click: function() {
							$( this ).dialog( "close" ); 
						} 
					},
					{
						text: "确认",
						"class" : "btn btn-primary btn-minier",
						click: function() {
							
							$( this ).dialog( "close" ); 
						} 
					}
				]
			});
			if(mainFormFieldId != mainFormFieldIdTag){
				mainFormFieldIdTag = mainFormFieldId;
				// 取到form表单dom
				var formDom = $('form',$("#exp"));
				// 获取所有的内部元素
				var formChild = formDom.children();
				// 删除所有的元素
				formChild.remove();
				formDom.text(null);
			}
			setFormFieldEls(columns,{act:""},formDom);
		});
		
	}

	var formEl = {
		'label' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var input = domlabel(id,"col-xs-10 col-sm-9 control-label" + ((list && list.className) || ""),value);
			controls.appendChild(input);
			// 提示信息
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3");
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			return div;
		},
		'hidden' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv();
			div.appendChild(controls);
			var input = dominput(id,type,value);
			controls.appendChild(input);
			return div;
		},
		'text' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			var input = dominput(id,type,value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
			controls.appendChild(input);
			if(required === true){
				/*var valitaionConf = "required:true";*/
				$(input).addClass("required")
				/*$(input).addClass("{"+valitaionConf+"}")*/
			}
			// 提示信息
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			if(size && size > 0){
				input.maxLength = size;
				$(input).inputlimiter({
					remText: '还可以输入 %n ...',
					limitText: '最多输入 : %n.'
				});
			}
			
			return div;
		},
		'ip' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			var input = dominput(id,"text",value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
			controls.appendChild(input);
			// 提示信息
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help || "请输入IPV4格式的IP地址");
			controls.appendChild(helpSpan);
			$(input).ipAddress()
			return div;
		},
		'number' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			var input = dominput(id,"text",value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
			controls.appendChild(input);
			// 提示信息
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help || "请输入整形数字");
			controls.appendChild(helpSpan);
			
			$(input).inputmask({ "mask": "9", "repeat": (size || 10), "greedy": false });
			return div;
		},
		'phone' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			var input = dominput(id,"text",value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
			controls.appendChild(input);
			// 提示信息
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help || "只能输入11位手机号");
			controls.appendChild(helpSpan);
			
			$(input).inputmask("19999999999");
			return div;
		},
		'password' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			var input = dominput(id,type,value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
			controls.appendChild(input);
			if(required === true){
				var valitaionConf = "required:true";
				
				$(input).addClass("{"+valitaionConf+"}")
			}
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);	
			if(list && list.equalTo === true){
				var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right","确认"+columnTitle);
				columnName.for=id;
				div.appendChild(columnName);
				var controls = domdiv(null,"col-sm-11");
				div.appendChild(controls);
				// 输入框
				var input = dominput(id+"_to",type,value,"col-xs-10 col-sm-9",'请输入'+columnTitle);
				controls.appendChild(input);
				if(required === true){
					var valitaionConf = "required:true,equalTo:'#"+id+"'";
					
					$(input).addClass("{"+valitaionConf+"}")
				}
				// 必填
				var requiredSpan = domspan('required_'+id+"_to","help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
				controls.appendChild(requiredSpan);
				// 提示信息
				var helpSpan = domspan('help_'+id+"_to","help-block help-inline col-xs-12 col-sm-8",help);
				controls.appendChild(helpSpan);
				
			}
			return div;
		},
		'textarea' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			// 输入框
			// id,type,value,className,rows
			var input = domtextarea(id,type,value,"col-xs-10 col-sm-9 autosize-transition",4);
			controls.appendChild(input);
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			if(size && size > 0){
				input.maxLength = size;
				$(input).inputlimiter({
					remText: '还可以输入 %n ...',
					limitText: '最多输入 : %n.'
				});
			}
			return div;
		},
		'zTree' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			if(!list)
				list = {};
			var disabledNodes = function(t,nodes,inheritParent, inheritChildren){
				var setDisabled = function(t,nodes,inheritParent,inheritChildren){
					for (var i=0, l=nodes.length; i < l; i++) {
						t.setChkDisabled(nodes[i], true,inheritParent,inheritChildren);
					}
				}
				
				if(list.notEdit === true){
					setDisabled(t,nodes);
				}else if(XY.Util.getType(list.notEdit) == "string"){
					try{
					if(!eval(list.notEdit))
						setDisabled(t,nodes);
					}catch(e){
						
					}
				}
			}
			var treeRun = function(ztreeDiv,zNodes,url,value){
				var t = $(ztreeDiv);
				
					var setting = {
					async: {
						enable: true,
						url: url,
						contentType: "application/json",
						autoParam: ["id"]
					},
					check: {
						// 设置 zTree 的节点上是否显示 checkbox / radio;true / false 分别表示
						// 显示 / 不显示 复选框或单选框
						enable: true,
						nocheckInherit: true,// 当父节点设置 nocheck = true
												// 时，设置子节点是否自动继承 nocheck = true
												// 。
					},
					view: {
						dblClickExpand: false,// 双击节点时，是否自动展开父节点的标识
						showLine: true,// 设置 zTree 是否显示节点之间的连线。
						selectedMulti: false,// 设置是否允许同时选中多个节点。
						txtSelectedEnable:true// 设置 zTree 是否允许可以选择 zTree DOM
												// 内的文本。
					},
					data: {
						key:{
							checked : "checked",// zTree 节点数据中保存 check
												// 状态的属性名称。默认值："checked"
							children : "children", // zTree
													// 节点数据中保存子节点数据的属性名称。默认值："children"
							name: "name", // zTree 节点数据保存节点名称的属性名称。默认值："name"
							
							// zTree 节点数据保存节点提示信息的属性名称。[setting.view.showTitle =
							// true 时生效]如果设置为 "" ，则自动与 setting.data.key.name
							// 持一致，避免用户反复设置默认值：""
							title: "name",
						},
						simpleData: {
							// 确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的
							// newNodes 数据是否采用简单数据模式 (Array)不需要用户再把数据库中取出的 List
							// 强行转换为复杂的 JSON 嵌套格式默认值：false
							enable:true,
							// 节点数据中保存唯一标识的属性名称。[setting.data.simpleData.enable
							// = true 时生效]默认值："id"
							idKey: "id",
							// 节点数据中保存其父节点唯一标识的属性名称。[setting.data.simpleData.enable
							// = true 时生效]默认值："pId"
							pIdKey: "pid",
							// 用于修正根节点父节点数据，即 pIdKey
							// 指定的属性值。[setting.data.simpleData.enable = true
							// 时生效]默认值：null
							rootPId: null
						}
					},
					callback: {
						beforeClick: function(treeId, treeNode) {
							if(list.notEdit === true){
								return false;
							}else if(XY.Util.getType(list.notEdit) == "string"){
								try{
								if(!eval(list.notEdit))
									return false;
								}catch(e){
									
								}
							}
							var zTree = $.fn.zTree.getZTreeObj(treeId);
							if (treeNode.isParent) {
								zTree.expandNode(treeNode);
								return false;
							} else {
								zTree.checkNode(treeNode,!treeNode.checked);
								return true;
							}
						},
						beforeCheck : function(treeIds, tree){
							disabledNodes(t,[tree],true,true);
							return tree;
						},
						onCheck : function(event, treeIds, tree){
							disabledNodes(t,[tree],true,true);
							return tree;
						},
						onExpand : function(event, treeIds, tree){
							disabledNodes(t,[tree],true,true);
							if(list.notEdit === true){
								return false;
							}else if(XY.Util.getType(list.notEdit) == "string"){
								try{
								if(!eval(list.notEdit))
									return false;
								}catch(e){
									
								}
							}
							return tree;
						}
					}
				};
				t = $.fn.zTree.init(t, setting, zNodes);
				
					setTimeout(function(){
						if(value && value.length > 0){
							for(var n = 0; n < value.length; n++){
								var node = t.getNodesByParam('id',value[n].id);
								for(var i = 0; i < node.length; i++){
									var tree = node[i];
									t.checkNode(tree,true,false,true);
								}
							}
						}
						disabledNodes(t,t.getNodes(),true,false);
					},500);
				
				if(list.notEdit === true){
					t.setChkDisabled(false);
				}else if(XY.Util.getType(list.notEdit) == "string"){
					try{
					if(!eval(list.notEdit))
						t.setChkDisabled(false);
					}catch(e){
						
					}
				}
			}
			
			// 视图
			var formGroup　= "form-group col-xs-12 col-sm-12";
			var columnNamecls = 2;
			var controlscls = 10;
			if(list && (list.col > 1)){
				var colNum = 12/list.col;
				columnNamecls +=2;
				controlscls -=2;
				formGroup　= "form-group col-xs-"+colNum+" col-sm-"+colNum;
			}
			var div = domdiv(null,formGroup);
			var columnName = domlabel("title_"+id,"col-sm-"+columnNamecls+" col-md-"+columnNamecls+" control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-"+controlscls+" col-md-"+controlscls);
			div.appendChild(controls);
			var controlstree = domdiv(null,"col-sm-10 col-md-9 no-padding-left no-padding-right");
			controls.appendChild(controlstree);
			var widgetbox = domdiv(null,"widget-box");
			controlstree.appendChild(widgetbox);
			var widgetheader = domdiv(null,"widget-header");
			widgetbox.appendChild(widgetheader);
			var widgettitletotal = domspan("widgettitletotal","widget-title",columnTitle);
			widgetheader.appendChild(widgettitletotal);
			var widgettoolbar = domdiv(null,"widget-toolbar");
			widgetheader.appendChild(widgettoolbar);
			var collapseA = doma();
			collapseA.setAttribute("data-action","collapse");
			widgettoolbar.appendChild(collapseA);
			var upI = domi(null,"ace-icon fa fa-chevron-up");
			collapseA.appendChild(upI);
			var widgetbody = domdiv(null,"widget-body");
			widgetbox.appendChild(widgetbody);
			var widgetmain = domdiv("tree_" + id,"ztree");
			widgetbody.appendChild(widgetmain);
			if(list){
				// 加载数据
				if(list.ajaxUrl && list.ajaxUrl.match('^'+XY.Util.app.contentPath)){
					
					$.ajax({
						"dataType":"json",
						"type":"post",
						"url":list.ajaxUrl,
						"asyn":false,
						"success":function(data){
							if(XY.Util.getType(data) === "array" && data.length > 0){
								treeRun(widgetmain,data,list.ajaxUrl,value);
							}
						}
					});
					
				}else if(XY.Util.getType(list.data) === "array"){
					treeRun(widgetmain,list,null,value);
				}
			}
			
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8 no-padding-left no-padding-right",help);
			controls.appendChild(helpSpan);
			
			return div;
		},
		'select' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			columnName.for=id;
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var selectSm = 9,spanXs = 10;
			if(formSelectExp && formSelectExp.status === true){
				selectSm = 7;
				spanXs = 8;
				
			}
			/**
			 * "formSelectExp":{ "status":true, "ajaxUrl":"", "action":"edit",
			 * "title":"状态列表" }
			 */
			var selectDiv = domdiv(null,"col-xs-"+spanXs+" col-sm-"+selectSm+" no-padding-left no-padding-right");
			controls.appendChild(selectDiv);
			var selectListRun = function(values){// 选择框
				if(XY.Util.getType(value) === "object")
					value = value.id;
				var select = domselect(id,values,value,"chosen-select form-control",'请选择'+columnTitle);
				selectDiv.appendChild(select);
			}
			if(list && list.ajaxUrl && list.ajaxUrl.match('^'+XY.Util.app.contentPath)){
				var dataid = "?";
				if(list.id === true){
					dataid += "id="+($('input[type="hidden"]').val()||"");
				}else if(list.id && list.id.match('\^\\d\$')){
					dataid += "id="+list.id;
				}
				if(list.parent != "undefined" && list.parent != null && XY.Util.getType(list.parent) == "boolean"){
					var parent = "isParent="+list.parent
					dataid += (dataid == "?" ? parent : "&"+parent);
				}
				if(list.parentid  && XY.Util.getType(list.parentid) == "number"){
					var parentid = "parentid="+list.parentid
					dataid += (dataid == "?" ? parentid : "&"+parentid);
				}
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":list.ajaxUrl + dataid,
					"asyn":false,
					"success":function(data){
						if(XY.Util.getType(data) === "array"){
							selectListRun(data);
						}
					}
				});
			}else{
				selectListRun(list.data);
			}
			
			if(formSelectExp && formSelectExp.status === true){
				var expDiv = doma(id+"_exp",null,"扩展","col-xs-2 col-sm-2 btn btn-info btn-white");
				// expDiv.setAttribute("data-toggle","modal");
				controls.appendChild(expDiv);
				var i = domi(null,"ace-icon fa fa-exchange");
				expDiv.appendChild(i);
				dialogClickFun(expDiv,formSelectExp.title);
			}
			// formSelectExp.ajaxUrl
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-2 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			
			return div;
		},
		'selectbox' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var controlstree = domdiv(null,"col-sm-9 no-padding-left no-padding-right");
			controls.appendChild(controlstree);
			var widgetbox = domdiv(null,"widget-box");
			controlstree.appendChild(widgetbox);
			var widgetheader = domdiv(null,"widget-header");
			widgetbox.appendChild(widgetheader);
			var widgettitletotal = domspan("widgettitletotal","widget-title","选中 _TATOL_");
			widgetheader.appendChild(widgettitletotal);
			var widgettoolbar = domdiv(null,"widget-toolbar");
			widgetheader.appendChild(widgettoolbar);
			var collapseA = doma();
			collapseA.setAttribute("data-action","collapse");
			widgettoolbar.appendChild(collapseA);
			var upI = domi(null,"ace-icon fa fa-chevron-up");
			collapseA.appendChild(upI);
			/**
			 * widget-toolbar <div class="widget-toolbar"> <a href="#"
			 * data-action="collapse"> <i class="ace-icon fa fa-chevron-up"></i>
			 * </a>
			 * 
			 * <a href="#" data-action="close"> <i class="ace-icon fa fa-times"></i>
			 * </a> </div>
			 */
			var widgetbody = domdiv(null,"widget-body");
			widgetbox.appendChild(widgetbody);
			var widgetmain = domdiv(null,"widget-main padding-8");
			widgetbody.appendChild(widgetmain);
			
			for(val in list){
				if((value != null || value != undefined) && val.toString() == value.toString()){
					$(radioName).append('<div class="col-sm-4"><input class="ace" type="radio" name="'+id+'" value="'+val+'" checked><span class="lbl">'+list[val]+'</span></div>');
				}else{
					$(radioName).append('<div class="col-sm-4"><input class="ace" type="radio" name="'+id+'" value="'+val+'"><span class="lbl">'+list[val]+'</span></div>');
				}
			}
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			return div;
		},
		'radio' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var radiobox = domdiv(null,"col-sm-9 no-padding-left no-padding-right");
			controls.appendChild(radiobox);
				
			var selectListRun = function(values){
				for(var i = 0 ;i < values.length; i ++){
					var val = values[i];
					var radioName = domlabel(null,"radio col-sm-4");
					radiobox.appendChild(radioName);
					if(val){
						if((value != null || value != undefined) && val.id.toString() == value.toString()){
							$(radioName).append('<div class="col-sm-12"><input class="ace" type="radio" name="'+id+'" value="'+val.id+'" checked><span class="lbl">'+val.name+'</span></div>');
						}else{
							$(radioName).append('<div class="col-sm-12"><input class="ace" type="radio" name="'+id+'" value="'+val.id+'"><span class="lbl">'+val.name+'</span></div>');
						}
					}
				}
			}
			if(list && list.ajaxUrl && list.ajaxUrl.match('^'+XY.Util.app.contentPath)){
				var id;
				if(list.id === true){
					id = "?id="+$('input[type="hidden"]').val();
				}else if(list.id && list.id.match('\^\\d\$')){
					id = "?id="+list.id;
				}
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":list.ajaxUrl,
					"asyn":false,
					"success":function(data){
						if(XY.Util.getType(data) === "array"){
							selectListRun(data);
						}
					}
				});
			}else{
				selectListRun(list.data);
			}
			
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			return div;
		},
		
		'checkbox' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var checkbox = domdiv(null,"col-sm-10 no-padding-left no-padding-right");
			controls.appendChild(checkbox);
			var radioName = domlabel(null,"radio col-sm-4");
			checkbox.appendChild(radioName);
			if((value != null || value != undefined) && value == true){
				$(radioName).append('<div class="col-sm-12"><input class="ace" value="true" type="checkbox" id="'+id+'" name="'+id+'" checked><span class="lbl"></span></div>');
			}else{
				$(radioName).append('<div class="col-sm-12"><input class="ace" value="true" type="checkbox" id="'+id+'" name="'+id+'" ><span class="lbl"></span></div>');
			}
			
			//必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			//提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			return div;
		},
		'dateTime' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var inputDiv = domdiv(null,"col-xs-10 col-sm-9 input-group");
			controls.appendChild(inputDiv);
			var timevalue = value ? XY.Util.dateFormat("yyy-MM-dd HH:mm:ss",value) : "";
			// 输入框
			var input = dominput(id,type,timevalue,"form-control",'请输入'+columnTitle);
			inputDiv.appendChild(input);
			var addonSpan = domspan(null,"input-group-addon");
			inputDiv.appendChild(addonSpan);
			addonSpan.appendChild(domi(null,"fa fa-clock-o bigger-110"));
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			$(input).inputmask("y-m-d h:m:s"); // multi-char placeholder
			$(input).datetimepicker({
				  locale:'zh-cn',
				  format: format || "YYYY-MM-DD HH:mm:ss",
				  // defaultDate : new Date(),
				  // viewMode:'years',
				  // inline: true,
				  // sideBySide:true,
				  icons: {
					time: 'fa fa-clock-o',
					date: 'fa fa-calendar',
					up: 'fa fa-chevron-up',
					down: 'fa fa-chevron-down',
					previous: 'fa fa-chevron-left',
					next: 'fa fa-chevron-right',
					today: 'fa fa-arrows ',
					clear: 'fa fa-trash',
					close: 'fa fa-times'
				 }
			  }).next().on(ace.click_event, function(){
					$(this).prev().focus();
				});
			return div;
		},
		'date' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var inputDiv = domdiv(null,"col-xs-10 col-sm-9 input-group");			
			controls.appendChild(inputDiv);
			var timevalue = value ? XY.Util.dateFormat("yyy-MM-dd",value) : null;
			// 输入框
			var input = dominput(id,"text",timevalue,"form-control date-picker",'请输入'+columnTitle);
			input.setAttribute("data-date-format","yyyy-mm-dd");
			inputDiv.appendChild(input);
			var addonSpan = domspan(null,"input-group-addon");
			inputDiv.appendChild(addonSpan);
			addonSpan.appendChild(domi(null,"fa fa-calendar bigger-110"));
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			$(input).inputmask("y-m-d",{ "placeholder": "yyyy-mm-dd" }); // multi-char
																			// placeholder
			$(input).datepicker({
				autoclose: true,
				language:'zh-cn',
				todayHighlight: true,
				startDate:timevalue ? new Date(timevalue) : '-1',
				startDate: -Infinity,
				endDate: Infinity,
				startView: 0,
				todayBtn: true
			}).next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
			return div;
		},
		'time' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			div.appendChild(controls);
			var inputDiv = domdiv(null,"col-xs-10 col-sm-9 input-group");			
			controls.appendChild(inputDiv);
			var timevalue = value ? XY.Util.dateFormat("HH:mm",value) : "";
			// 输入框
			var input = dominput(id,"text",timevalue,"form-control",'请输入'+columnTitle);
			inputDiv.appendChild(input);
			var addonSpan = domspan(id+"_toggle","input-group-addon");
			inputDiv.appendChild(addonSpan);
			addonSpan.appendChild(domi(null,"glyphicon glyphicon-time"));
			// 必填
			var requiredSpan = domspan('required_'+id,"help-block help-inline col-xs-12 col-sm-3",requiredFn(required));
			controls.appendChild(requiredSpan);
			// 提示信息
			var helpSpan = domspan('help_'+id,"help-block help-inline col-xs-12 col-sm-8",help);
			controls.appendChild(helpSpan);
			$(input).clockface({
				format: 'HH:mm'
			});
			$(addonSpan).click(function (e) {
				e.stopPropagation();
				$(input).clockface('toggle');
			});
			return div;
		},
		'table' : function(id,type,columnTitle,value,size,help,required,list,format,formSelectExp){
			var div = domdiv(null,"form-group");
			var columnName = domlabel("title_"+id,"col-sm-1 control-label no-padding-right",columnTitle);
			div.appendChild(columnName);
			var controls = domdiv(null,"col-sm-11");
			var controlss = domdiv(null,"col-sm-9 no-padding-left no-padding-right");
			var tableDiv = domtable(id,"table table-striped table-bordered table-hover");
			controlss.appendChild(tableDiv);
			controls.appendChild(controlss);
			div.appendChild(controls);

			// 添加表头
			var theadDiv = domthead();
			tableDiv.appendChild(theadDiv);
			// 添加表头的tr
			var trDiv = domtr(null,"center");
			theadDiv.appendChild(trDiv);

			var idval;
			if(list.id === true){
				idval = "?id="+$('input[type="hidden"]').val();
			}else if(list.id && list.id.match('\^\\d\$')){
				idval = "?id="+list.id;
			}
			setTimeout(function(){
				var tables = $("#"+id).DataTable({
					"bServerSide":true,// 指定从服务器端获取数据
					"bPaginate" : false, // 是否显示（应用）分页器
					"searching" : false,
					"aoColumns":list.columns,
					"ajax":list.ajaxUrl+idval,
					"oLanguage": {
						"sProcessing":"正在获取数据,请稍后...",
						"sEmptyTable":"无数据...",
						"sLengthMenu": "每页显示 _MENU_",
						"sInfo":"当前第 _START_ 到 _END_ 条 / 共 _TOTAL_ 条数据",
						"sInfoEmpty":"没有数据",
						"sInfoFiltered":"数据表中共为_MAX_条数据",
						"sZeroRecords":"没有查到符合的数据...",
						"selected":"选中_MAX_条",
						"sSearch":"搜索:"
					}
					
				});
			},100)
			return div;
		},
		'button' : function(id,type,className,iconClass,text){
			var buttonDiv = dombutton(id,type,null,className);
			var iconDiv = domi(null,iconClass,text);
			buttonDiv.appendChild(iconDiv);
			return buttonDiv;
		}
		
	};
	var setFormFieldEls = function(columns,config,formDom,buttons){
		// var div = "<div style="margin:10px 0px"></div>";
		formDom.append(div);
		for(var i =0; i < columns.length;i ++){
			if(!columns[i] || columns[i].form == false)
				continue;
			// 获取字段的fieldType对应的函数
			var el = formEl[columns[i].fieldType];
			if(el){
				var dataVal = null;
				// 获取选中记录的当前字段的编辑框显示数据的顶层对象（如果存在a.b结构）或属性（不存在a.b结构）
				var tempData = eval('config.data.'+columns[i].field.split('.')[0]);
				if(tempData != undefined || tempData != null){
					//新建指标时，data里面只有id字段，且为0
					dataVal = eval('config.data.'+columns[i].field);
				}
				var val = columns[i].value || dataVal;
				formDom.append(el(columns[i].field,columns[i].fieldType,columns[i].title,val,columns[i].size,columns[i].help,columns[i].required,columns[i].list,columns[i].format,columns[i].formSelectExp));
			}
		}
		// 添加按钮(buttons不是column的字段，故需要另外处理)
		if(buttons != null){
			// 创建按钮条div
			// var div = domdiv(null,"clearfix form-actions");
			var div = domdiv(null,"form-group");
			var spaceholder = domdiv(null,"col-sm-4 no-padding-right");
			div.appendChild(spaceholder);
			var controls = domdiv(null,"col-sm-8");
			div.appendChild(controls);
			// 获取button函数
			var butfuc = formEl['button'];
			if(butfuc){
				// 如果按钮组只有一个按钮，则该按钮为“确定”按钮
				if(buttons.length == 1){
					// “确定”按钮的样式设置
					buttons[0].id = "but-save";
					buttons[0].type = "button";
					buttons[0].className = "btn btn-light";
					buttons[0].iconName = "ace-icon fa fa-check bigger-110";
					var buttonDiv = butfuc(buttons[0].id,buttons[0].type,buttons[0].className,buttons[0].iconName,buttons[0].value);
					controls.appendChild(buttonDiv);
					formDom.append(div);
				}
				// 如果按钮组有两个按钮，则按钮为“确定”按钮和“取消”按钮
				if(buttons.length == 2){
					for(var i =0; i < buttons.length; i++){
						if(i == 0){
							// "确定"按钮的样式设置
							buttons[i].id = "but-save";
							buttons[i].type = "button";
							buttons[i].className = "btn btn-light";
							buttons[i].iconName = "ace-icon fa fa-check bigger-110";
							var buttonDiv = butfuc(buttons[i].id,buttons[i].type,buttons[i].className,buttons[i].iconName,buttons[i].value);
							controls.appendChild(buttonDiv);
						}
						if(i == 1){
							// "取消"按钮的样式设置
							buttons[i].id = "but-close";
							buttons[i].type = "button";
							buttons[i].className = "btn btn-light";
							buttons[i].iconName = "ace-icon fa fa-undo bigger-110";
							var buttonDiv = butfuc(buttons[i].id,buttons[i].type,buttons[i].className,buttons[i].iconName,buttons[i].value);
							controls.appendChild(buttonDiv);
						}
					}
					$('#'+buttons[0].id).css({margin:"0 20px 10px 0"});
					formDom.append(div);
				}
				if(buttons.length == 3){
					for(var i =0; i < buttons.length; i++){
						if(i == 0){
							// "保存到草稿"按钮的样式设置
							buttons[i].id = "saveDraft";
							buttons[i].type = "button";
							buttons[i].className = "btn btn-light";
							buttons[i].iconName = "ace-icon fa fa-pencil";
							var buttonDiv = butfuc(buttons[i].id,buttons[i].type,buttons[i].className,buttons[i].iconName,buttons[i].value);
							controls.appendChild(buttonDiv);
						}
						if(i == 1){
							// "确定"按钮的样式设置
							buttons[i].id = "but-save";
							buttons[i].type = "button";
							buttons[i].className = "btn btn-light";
							buttons[i].iconName = "ace-icon fa fa-check bigger-110";
							var buttonDiv = butfuc(buttons[i].id,buttons[i].type,buttons[i].className,buttons[i].iconName,buttons[i].value);
							controls.appendChild(buttonDiv);
						}
						if(i == 2){
							// "取消"按钮的样式设置
							buttons[i].id = "but-close";
							buttons[i].type = "button";
							buttons[i].className = "btn btn-light";
							buttons[i].iconName = "ace-icon fa fa-undo bigger-110";
							var buttonDiv = butfuc(buttons[i].id,buttons[i].type,buttons[i].className,buttons[i].iconName,buttons[i].value);
							controls.appendChild(buttonDiv);
						}
					}
					
					formDom.append(div);
					$('#'+buttons[0].id).css({margin:"10px 0 10px 40px"});
					$('#'+buttons[1].id).css({margin:"10px 0 10px 20px"});
					$('#'+buttons[2].id).css({margin:"10px 0 10px 20px"});
				}
			}
			
			
		}
		
	};
	
    return {
    	saveUrl:{},
		columns : {},
		table : null,
		Load : function(config){
			//config参数由dtExtend.js中的edit函数self传过来，属性有act，columns，data，saveUrl，table
			this.table = config.table;
			this.editBoxId = config.editBoxId;
			this.columns = config.columns;
			this.buttons = config.buttons;
			// 取到form表单dom
			var formDom = $('form',$(this.editBoxId));
			if(formDom && formDom.length <= 0)
				formDom = $('form',$('#edit'))
			// 获取所有的内部元素
			var formChild = formDom.children();
			// 删除所有的元素
			formChild.remove();
			formDom.text(null);
			// 设置表单元素
			setFormFieldEls(this.columns,config,formDom,this.buttons);
			// 输入框自动设置大小
			autosize($('textarea[class*=autosize]'));
			setTimeout(function(){
				$('select',$('form',$(this.editBoxId))).chosen({allow_single_deselect:true});
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				// resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().parent().width()});
					})
				});
				$('label[id=code]').css({"color":"#dd4b39"});
				/*formatTitle();*/
			},50);
			XY.Util.app.setBodyHeight($('.modal-body',$('#editBox')),200);
			//编辑信息验证
			$('form',$(this.editBoxId)).validate();
		},
        // main function to initiate the editBox
        init: function (config) {
			this.saveUrl = config.saveUrl;
			var table = FormSamples.table;
			var editBoxId = config.editBoxId || '#edit';
			var getData = function(addobj){
				//过滤值为空或指定符号
				var form = $('form',$(editBoxId));
				form.attr('action',FormSamples.saveUrl);
				var formData = form.serializeArray();
				var data = {"cmd":form.attr('cmd')}
				var table = FormSamples.table;
				for(var i = 0; i < formData.length; i ++){
					var keyVal = formData[i];
					var key = keyVal.name;
					var val = keyVal.value;
					if(val && val != "" && val != "___.___.___.___"){
						data[key] = val;
					}
				}
				var columns = FormSamples.columns;
				columns.forEach(function(col){
					if(col.fieldType == "zTree"){
						var tree_data = [];
						var trees = $.fn.zTree.getZTreeObj('tree_'+col.field);
						if(trees){
							var treeNodes =  trees.getChangeCheckedNodes();
							treeNodes.forEach(function(tree,index,trees){
								tree_data.push({id:tree.id});
							});
							data[col.field] = tree_data;
						}
					}else if(col.list && col.list.object == true){
						data[col.field] = {id:data[col.field]};
					}
				});
				for(var p in addobj){
					data[p] = addobj[p];
				}
				return data;
			}
			// 点击保存按钮
			$('#but-save',$(editBoxId)).click(function(e){
				$.ajax({
					"contentType":"application/json;charset=UTF-8",
					"dataType":"json",
					"type":"post",
					"url":FormSamples.saveUrl,
					"data":JSON.stringify(getData({"isDraft":false})),
					"success":function(data){
						FormSamples.table.bootstrapTable("refresh");
						//隐藏表单
						var dialog = $('.modal-dialog',$(editBoxId));
						if(dialog.length > 0){
							$(editBoxId).modal('hide');
						}
						// 隐藏表单
						$(editBoxId).animate({
							height:'hide'
						},300);
					}
				});
				
			});
			//点击保存为草稿按钮
			$('#saveDraft',$(editBoxId)).click(function(e){
				$.ajax({
					"contentType":"application/json;charset=UTF-8",
					"dataType":"json",
					"type":"post",
					"url":FormSamples.saveUrl,
					"data":JSON.stringify(getData({"isDraft":true})),
					"success":function(data){
						FormSamples.table.bootstrapTable("refresh");
						// 隐藏表单
						$(editBoxId).animate({
							height:'hide'
						},300);
					}
				});
			});
			// 点击关闭按钮
			$('#but-close',$(editBoxId)).click(function(e){
				//点击Model关闭按钮
				var dialog = $('.modal-dialog',$(editBoxId));
				if(dialog.length > 0){
					$(editBoxId).modal('hide');
				}
				//隐藏表单
				$(editBoxId).animate({
					height:'hide'
				},300);
				var form = $('form',$(editBoxId));
				var children = form.children();
				children.each(function(index,dom){dom.remove()});
				FormSamples.columns = null;
			});
			
        }
    };
}();