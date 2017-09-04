Z = XY = Object(function(data){
	var zero,
		self = XY,
		doc = document,
		getType = self.Util.getType,
		cp = self.Util.cpObj;
	if(data){
		//
		var dataType = getType(data);
		//如果data对象是一个dom数组类型为htmlDIV元素(DOM)或htmlhtmlDIV元素集合
		if(dataType == "htmldivelement" || dataType == "htmlcollection"){
			zero = data;
		//如果data对象是字符串
		}else if(dataType == "string"){
			//查询字符在字符串当中的位置
			var inOf = function(d){
				return data.indexOf(d)
			},
			//截取字符串第一位以后的数据
			tag = data.substring(1);
			
			if(inOf('#') == 0){
				zero = doc.getElementById(tag);
			}else if (inOf('.') == 0){
				zero = doc.getElementsByClassName(tag);
			}else if (inOf('<') == 0){
				zero = doc.getElementsByTagName(tag);
			}else{
				zero = doc.getElementsByTagName(data);
			}
		}
	}else{
		//如果参数为空,则赋值为body
		zero = doc.body;
	}
	if(zero){
		//复制XY框架的属性
		if(getType(zero) == "Array" && zero.length){
			for(var i = 0 ; i < zero.length;  i ++){
				cp(zero[i],self);
			}
			cp(zero,self);
		}else{
			cp(zero,self);
		}
		return zero;		
	}
	return self;
});
Util = new Object();
XY.Util = Util;
Util.isRTL = function () {
	if ($('body').css('direction') === 'rtl') {
		return true;
	};
	return false;
}
// 类型判断
XY.Util.getType = function (o) {
	var _t;
	return ((_t = typeof(o)) == 'object' ? o == null && 'null'
			|| Object.prototype.toString.call(o).slice(8, -1) : _t)
			.toLowerCase();
};
/**
注册命名空间
zero.xiao
2014-12-04
调用命名空间方法
一 Util.register(nameSpace,type,config);
二 Util.register(nameSpace,type);//
三 Util.register(nameSpace,config);//如果命名空间已注册,则复制config对象属性覆盖原有属性
参数说明
参数nameSpace:命名空间,字符串类型
参数type:注册的类型
参数config:初始化的成员属性

*/
Util.register = function(){
	if(!arguments || arguments.length == 0) return;
	//声明并取出参数
	var nameSpace,type,config;
	if(arguments.length >= 1){
		nameSpace = arguments[0];
	}
	if(arguments.length >= 2){
		type = arguments[1];
	}
	if(arguments.length == 3){
		
		config = arguments[2];
	} 
	//如果参数config为假,则判断type是否为空对象,如果非空,则把type赋给config并设为null
	if(!config){
		for(var p in type){
			if(p){
				config = type;
				type = null;
				break;
			}
		}
	}

	var objType = 'Object';
	if(type){
		if(typeof type == 'object'){
			objType = 'Object';
		}else if(typeof type == 'function'){
			objType = 'Function';
		}else if(typeof type == 'string'){
			if(type.toLocaleLowerCase() == 'object'){
				objType = 'Function';
			}
			if(type.toLocaleLowerCase() == 'function'){
				objType = 'Object';
			}
		}
	}
	//如果命名空间未注册	
	if(XY.Util.getType(nameSpace) === "string" || !eval(nameSpace)){
		var nameArr = nameSpace.split('.');
		var nameEval = '';
		var nameNs = '';		
		//创建对象
		for(var i = 0;i < nameArr.length;i ++){
			if(i != 0){
				nameNs += '.';
			}
			nameNs += nameArr[i];
			nameEval += 'if(typeof('+nameNs+')=="undefined"){'+nameNs+' = new '+objType+'();}'
			if(nameEval != ''){
				eval(nameEval);
			}
		}
	}
	//设置对象成员
	var objEl = eval(nameSpace);
	if(config){
		if(objType =='Object'){
			for(var p in config){
				objEl[p] = config[p];
			}
		}
		if(objType == 'Function'){
			for(var p in config){
				objEl.prototype[p] = config[p];
			}
		}
	}
	return this;
};
/**
Map
*/
Util.register('XY.Util.Map','object',{
		//单个添加
		put : function(key,value){
			this[key] = value;
			return this;
		},
		//添加对象
		each : function(object){
			for(var p in object){
				//把值赋给map
				this[p]  = object[p];
			}
			return this;
		},
		//单个查找
		get : function(key){
			return this[key];
		},
		//单个删除
		remove : function(key){
			delete this[key];
			return this;
		},
		all : function(){
			
		}
	}
);

/**
事件
*/
Util.register('XY','function',{
	css : function(){
		var setStyle = function(el,styles){
			for(var p in styles){
				el.style[p] = styles[p];
			}
		}
		var els = arguments[0];
		var styles = arguments[1];
		var type = XY.Util.getType(els);
		
		if(type == "function"){
			setStyle(this,els);
		}else if(XY.Util.getType(arguments[1])  == "function"){
			if(type == "htmldivelement"){
				setStyle(els,styles);
			}else if(type == "htmlcollection"){
				for(var i = 0 ; i < els.length;  i ++){
					setStyle(els[i],styles);
				}
			}
		}
	},
	//缓存事件执行函数
	eventFnMap : (function(){
		return new XY.Util.Map();
	})(),
	click : function(){
		if(!arguments || arguments.length == 0) return;
		//声明并取出参数
		var data,fn;
		for(var i = 0 ;i < arguments.length; i ++){
			if(i == 0){
				if(XY.Util.getType(arguments[i]) == "function"){
					data = this;
				}else{
					data = arguments[i];
				}
				if(arguments.length == 1){
					fn = arguments[i];
				}
			}
			if(i == 1){
				fn = arguments[i];
			}
		}
		//如果data对象是一个dom数组
		if(data.length){
			for(var i = 0 ; i < data.length;  i ++){
				data[i].onclick = fn;
			}
		}else{
			data.onclick = fn;
		}
	},
	clickFn : function(xy){
		this(xy).click(function(e){
			var id = this.id;
			bus.publish(function(){
				log(id);
			});
		});
	},
	bind : function(type,data,fn){
		data[type]	=	fn;
	},
	unbind : function(type,data){
		data[type] = null;
	},
	'delete': function(){
		if(this.length){
			for(var i = 0;i < this.length;i ++){
				this[i].remove();
			}
		}
		if(this.childNodes){
			this.removeChild().remove();
		}else{
			this.remove();
		}
	},
	removeChild	: function(){
		if(this.length){
			for(var i = 0;i < this.length;i ++){
				this[i].removeChild();
			}
		}
		var list = this.childNodes;
		
		for(var i = 0;i < list.length;i ++){
			list[i].remove();
		}
		if(list.length){
			this.removeChild();
		}else{
			return this;
		}
	}
});
Util.register('XY.Util.Url','function',{
	get	:	function(){ 
		return window.document.location.href.toString(); 
	},
	getParameter	:	function(key){
		var u = this.get().split('?'); 
			var url = {}; 
			if(typeof(u[1]) == 'string'){
				u = u[1].split('&');
				for(var i = 0 ; i < u.length;i ++){ 
					var j = u[i].split('='); 
					url[j[0]] = j[1]; 
				}
				if(key){
					return url[key];
				}
			}
			return url;
	},
	data : function(url,type,datas){
		//http://127.0.0.1:80/admin/menu/authType
		var data = {tm:new Date().getTime()};
		if(datas){
			for(var p in datas){
				data[p] = datas[p];
			};
		};
		var jsondata = {};
		$.ajax({
			type: type || "GET",
			url: url,
			data: data,
			//dataType:'json',
			async : false,
			success: function(data){
				jsondata = JSON.stringify(data.result || data.data || data);
			}
		});
		return jsondata;
	}
});
/**
*复制属性
	obj 目标对象
	fun 源对象
*/
Util.register('XY.Util','function',{
	cpObj : function(obj,fun){
		var self = XY.Util;
		if(!obj || !fun){
			return null;
		}
		for(var p in fun){
			//如果obj中有这个属性[p],则判断p是否为function
			if(obj[p] && self.getType(fun[p]) == 'object'){
				self.cpObj(obj[p],p);
			}
			obj[p] = fun[p];
		}
	}
});
//引入js和css文件
Util.register('XY.Util','function',{
	include : function(file){
		var jsP = function(file){
			var script = XY.Util.DOM('script');
			script.type = "text/javascript";
			script.language="javascript";
			script.src = file;
			document.write(script.outerHTML);
		}
		var cssP = function(file){
			var link = XY.Util.DOM('link');
			link.type = "text/css";
			link.language="javascript";
			link.rel="stylesheet";
			link.href = file;
			document.write(link.outerHTML);
		}
		//判断文件类型并调用写入函数
		var judge = function(f){
			if(f.substring((f.length-3)) == '.js'){
				jsP(f);
			}else if(f.substring((f.length-3)) == 'css'){
				cssP(f);
			}
		}
		if(XY.Util.getType(file) == "Array" && file.length){
			for(var i in file){
				judge(file[i]);
			}
		}else{
			judge(file);
		}
	}
});
//创建DOM对象
Util.register('XY.Util.DOM','function',{
	//创建任意标签
	createBase : function(tag,id,className,text){
		if(tag && XY.Util.getType(tag) == "string"){
			var div = document.createElement(tag);  
			if(id)div.id = id;
			div.className = className ? className : id ? id : '';
			if(text) div.innerHTML = text;
			return div;
		}
		return null;
	},
	//创建Div相对定位
	createDivRelative : function (id,className,text){
		var div = XY.Util.DOM.createBase('div',id,className,text);
		return div;
	},
	//创建Div绝对定位
	createDivABS : function(id,className,text){
		var div = XY.Util.DOM.createDivRelative(id,className,text);  
		div.style.position = 'absolute';                
		return div;
	},
	//创建A标签
	createA : function (id,href,text,className){
		var div = XY.Util.DOM.createBase('a',id,className,text); 
		if(href)div.href=href;
		return div;
	}, 
	//创建B标签
	createB : function (id,className,text){
		var div = XY.Util.DOM.createBase('b',id,className,text); 
		return div;
	}, 
	//创建label标签
	createLabel : function (id,className,text){
		var div = XY.Util.DOM.createBase('label',id,className,text); 
		return div;
	},
	//创建li标签
	createLi : function (id,className,text){
		var div = XY.Util.DOM.createBase('li',id,className,text); 
		return div;
	}, 
	//创建ul标签
	createUl : function (id,className,text){
		var div = XY.Util.DOM.createBase('ul',id,className,text);  
		return div;
	}, 
	//创建img
	createImg : function (id,src,width,height,alt){
		var img = XY.Util.DOM.createBase('img',id);  
		img.style.position = 'absolute'; 
		if(!height){
			img.style.top = 0;               
			img.style.bottom = 0; 
		}else{
			img.height = height;
		}
		if(!width){
			img.style.left = 0;               
			img.style.right = 0;  
		}else{
			img.width = width;  
		}
		img.src = src;                
		img.border = 0;                
		if(alt) img.alt = alt;
		return img;
	},
	//创建i标签
	createI : function (id,className,text){
		var div = XY.Util.DOM.createBase('i',id,className,text);  
		return div;
	}, 
	//创建span标签
	createSpan : function (id,className,text){
		var div = XY.Util.DOM.createBase('span',id,className,text);  
		return div;
	},  
	//创建input标签
	createForm : function (id,action,className,method,enctype){
		var div = XY.Util.DOM.createBase('form',id,className);  
		div.name = id;
		if(method == "POST")div.method = method;
		if(enctype)div.enctype = "multipart/form-data";
		if(action) div.action = action;
		return div;
	},  
	//创建input标签
	createInput : function (id,type,value,className,placeholder,readOnly){
		var div = XY.Util.DOM.createBase('input',id,className);  
		if(type) div.type = type;
		div.name = id;
		if(placeholder) div.placeholder = placeholder;
		if(readOnly === true) div.readOnly = true;
		if(value) div.value = value;
		return div;
	},  
	//创建textarea标签
	createTextarea : function (id,type,value,className,rows){
		var div = XY.Util.DOM.createBase('textarea',id,className);  
		if(type) div.type = type;
		div.name = id;
		div.rows = rows || 3;
		if(value) div.value = value;
		return div;
	},  
	//创建button标签
	createButton : function (id,type,value,className){
		var div = XY.Util.DOM.createBase('button',id,className);  
		if(type) div.type = type;
		div.name = id;
		if(value) div.value = value;
		return div;
	}, 
	//创建select标签
	createSelect : function (id,list,value,className,placeholder){
		var div = XY.Util.DOM.createBase('select',id,className);
		div.name = id;
		if(placeholder)div.setAttribute("data-placeholder",placeholder);
		var option = XY.Util.DOM.createBase('option',null,null,'请选择');
		option.value = '-1';
		div.appendChild(option);
		if(XY.Util.getType(list) === "array"){
			for(var i = 0 ; i < list.length ; i ++){
				var el = list[i];
				var option = XY.Util.DOM.createBase('option',null,null,el.name);
				option.value = el.id;
				if((value != null || value != undefined) && (el.id != null || el.id != undefined) && value.toString() === el.id.toString())
					option.selected = true;
				div.appendChild(option);
			}
		}
		return div;
	}, 
	//设置div样式
	setDivStyle : function (_div,_style){
		if(getType(_div) == 'array'){
			for(var i = 0;i < _div.length;i ++){
				_div[i].css(_style);
			}
		}else{
			_div.css(_style);
		}
	},
	//包装一层div
	packDiv : function (_div,style){
		if(div && div.Util && div.Util.getType(_div).indexOf('html')==0 && div.Util.getType(_div).indexOf('element')>0){
			var div = XY.Util.DOM.createDivRelative('setChart');
			setDivStyle($(div),style);
			div.appendChild(_div);
			return div;
		}
	},
	//删除子Dom
	removeDoms : function (div){
		if(div && div.Util && div.Util.getType(div).indexOf('html')==0 && div.Util.getType(div).indexOf('element')>0){
			var child = div.childNodes;
			for(var i = 0;i < child.length;i++){
				div.removeChild(child[i]);
			}
		}
	},
	//删除Dom
	removeDom : function (div){
			if(div)div.remove();
	},
	//隐藏Dom
	hideDom : function (div){
		var child = div.childNodes;
		for(var i = 0;i < child.length;i++){
			$(child[i]).hide();
		}
	}
});
Util.register('XY.Util','function',{
	/**
	时间戳转为时间
	*/
	getLong2Date : function(l){
		if(!l) return null;
		var i = parseInt(l);
		if(l.toString().length==10){
			i = parseInt(l) * 1000
		}
		var tt = new Date(i).toLocaleString().replace('年','-').replace('月','-').replace('日','').replace('上午','').replace('下午','');
		return tt;
	}
	/**
	时间转为时间戳
	*/
	,getDate2Long : function(d){
		if(!d) return null;
		var date = new Date(d);
		return Date.parse(date)/1000;
	}
	/**
	转为本地时间
	*/
	,getLocale : function(t){
		if(!t)	return null;
		return new Date(t).toLocaleString();
	}
});
/**
时间格式化
格式:XY.Util.dateFormat['yyy年MM月dd日周wHH:mm:ss']
*/
Util.register('XY.Util','function',{
	dateFormat :function(type,date){
		var dateLen = date.toString().length;
		if(XY.Util.getType(date) == "number" && dateLen < 13){
			for(var i = 0; i < (13 - dateLen); i ++){
				date = date * 10;
			}
		}
		if(XY.Util.getType(date) == "number" && dateLen > 13){
			for(var i = 0; i < (dateLen - 13); i ++){
				date = date / 10;
			}
		}
		var wk = {
					0:"周日",
					1:"周一",
					2:"周二",
					3:"周三",
					4:"周四",
					5:"周五",
					6:"周六"
				},time = new Date(date) || new Date();
				
		var format = {
			'yyy年MM月dd日 周w HH:mm:ss' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var week = time.getDay();//星期
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var Seconds = time.getSeconds();//秒
					Seconds = (Seconds < 10 ? "0" + Seconds : Seconds);
					var Milliseconds = time.getMilliseconds();//毫秒
					var date = Year + "年" + Month + "月" + Day + "日 " + wk[week] + " " + Hours + ":" + Minutes + ":" + Seconds; //+ ":" + Milliseconds;
					return date;
			},
			'yyy年MM月dd日 HH:mm:ss' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var Seconds = time.getSeconds();//秒
					Seconds = (Seconds < 10 ? "0" + Seconds : Seconds);
					var date = Year + "年" + Month + "月" + Day + "日 " + Hours + ":" + Minutes + ":" + Seconds;
					return date;
			},
			'yyy年MM月dd日 HH:mm' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var date = Year + "年" + Month + "月" + Day + "日 " + Hours + ":" + Minutes;
					return date;
			},
			'yyy年MM月dd日' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var date = Year + "年" + Month + "月" + Day + "日 ";
					return date;
			},
			'MM月dd日' : function(){
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var date = Month + "月" + Day + "日 ";
					return date;
			},
			'yyy年MM月dd日 周w HH:mm:' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var week = time.getDay();//星期
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var date = Year + "年" + Month + "月" + Day + "日 " + wk[week] + " " + Hours + ":" + Minutes;
					return date;
			},
			'yyy年MM月dd日 周w' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var week = time.getDay();//星期
					var date = Year + "年" + Month + "月" + Day + "日 " + wk[week];
					return date;
			},
			'MM月dd日 周w' : function(){
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var week = time.getDay();//星期
					var date = Month + "月" + Day + "日 " + wk[week];
					return date;
			},
			'yyy-MM-dd HH:mm:ss' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var Seconds = time.getSeconds();//秒
					Seconds = (Seconds < 10 ? "0" + Seconds : Seconds);
					var Milliseconds = time.getMilliseconds();//毫秒
					var date = Year + "-" + Month + "-" + Day  + " " + Hours + ":" + Minutes + ":" + Seconds; //+ ":" + Milliseconds;
					return date;
			},
			'yyy-MM-dd HH:mm' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var date = Year + "-" + Month + "-" + Day  + " " + Hours + ":" + Minutes;
					return date;
			},
			'yyy-MM-dd' : function(){
					var Year = time.getFullYear();//年
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var date = Year + "-" + Month + "-" + Day;
					return date;
			},
			'MM-dd' : function(){
					var Month = time.getMonth() + 1;//月
					Month = (Month < 10 ? "0" + Month : Month);
					var Day = time.getDate();//日
					Day = (Day < 10 ? "0" + Day : Day);
					var date = Month + "-" + Day;
					return date;
			},
			'HH:mm:ss' : function(){
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var Seconds = time.getSeconds();//秒
					Seconds = (Seconds < 10 ? "0" + Seconds : Seconds);
					var date = Hours + ":" + Minutes + ":" + Seconds;
					return date;
			},
			'HH:mm' : function(){
					var Hours = time.getHours();//时
					Hours = (Hours < 10 ? "0" + Hours : Hours);
					var Minutes = time.getMinutes();//分
					Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
					var date = Hours + ":" + Minutes;
					return date;
			}
		}
		return !type ? null : format[type] ? format[type]() : -1;
	}
});

Util.register('XY.Util','function',{
	dynamicTime : function(div){
		if(!div)div = XY('#dynamicTime');
		if(!div)div = XY('body');
		if(!XY.Util.getType(div).match('^html[A-Za-z]+element$')){
			div = XY(div);
		}
			var wk = {
				0:"周日",
				1:"周一",
				2:"周二",
				3:"周三",
				4:"周四",
				5:"周五",
				6:"周六"
			}
			var dom  = div;
			
			setInterval(function(){
				
				var time = new Date();
				var Year = time.getFullYear();//年
				var Month = time.getMonth() + 1;//月
				Month = (Month < 10 ? "0" + Month : Month);
				var Day = time.getDate();//日
				Day = (Day < 10 ? "0" + Day : Day);
				var week = time.getDay();//星期
				var Hours = time.getHours();//时
				Hours = (Hours < 10 ? "0" + Hours : Hours);
				var Minutes = time.getMinutes();//分
				Minutes = (Minutes < 10 ? "0" + Minutes : Minutes);
				var Seconds = time.getSeconds();//秒
				Seconds = (Seconds < 10 ? "0" + Seconds : Seconds);
				var Milliseconds = time.getMilliseconds();//毫秒
				var date = Year + "年" + Month + "月" + Day + "日 " + wk[week] + " " + Hours + ":" + Minutes + ":" + Seconds //+ ":" + Milliseconds;
				dom.innerText = date;
			},1);
		
	}
});