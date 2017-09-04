jQuery(function($){
	var $content = $('.page-content');
		$content.css({'margin-top':'6px'});
		$content.css({'padding-top':'2px'});
		$content.css({'padding-bottom':'2px'});
	var app = XY.Util.app;
	app.setBodyHeight($content);
})

var App = function(){
	var app = XY.Util.app;
	var setBodyHeight = function(mbody,toleranceHeight){
		if(!mbody) return;
		if(!toleranceHeight) toleranceHeight = 0;
		mbody = $(mbody);
		var diff = parseInt(mbody.css('margin-top'));
		diff = diff + 60 +parseInt(diff / 2);
		maxh = parseInt($(window).innerHeight() - diff);
		maxh -= $('#navbar').height();
		maxh -= $('#breadcrumbs').height();
		mbody.css({'max-height':maxh - toleranceHeight});
		return maxh;
	}
	
	function ajaxStatus(){
		$.ajaxSetup({
			type : "GET",
			complete : function(XMLHttpRequest,textStatus){
				var options = {
					backdrop : false,
					keyboard : false
				};
				if(XMLHttpRequest.responseText == "loginerror"){
					$('.text',$('#requestStatus')).html('登录超时！请重新登录');
					$('#ok',$('#requestStatus')).attr('did','login');
					$('#requestStatus').modal(options);
				}else if(textStatus == "timeout"){
					$('.text',$('#requestStatus')).html('请求超时！请稍后再试！\n\r'+XMLHttpRequest.status);
					$('#ok',$('#requestStatus')).attr('did','error');
					$('#requestStatus').modal(options);
				}else if(textStatus == "parsererror" || textStatus == "error"){
					$('.text',$('#requestStatus')).html('请求异常！请稍后再试！\n\r'+XMLHttpRequest.status);
					$('#ok',$('#requestStatus')).attr('did','error');
					$('#requestStatus').modal(options);
				}
				
			},
			success : function(xhr,data){
				//console.log(xhr,data);
			},
			error : function(xhr,data){
				//console.log(xhr,data);
			},
			start : function(xhr,data){
				//console.log(xhr,data);
			},
			beforeSend : function(xhr,data){
				//console.log(xhr,data);
			}
		});
		/**
		 * 当对用户可见时
		 */
		$('#ok',$('#requestStatus')).on('hide.bs.modal',function(e){
			if($(this).attr('did') == 'login'){
				$(this).attr('did','');
				window.location.href = ""+XY.Util.app.contentPath + XY.Util.app.loginPath;
			}else if($(this).attr('did') == 'error'){
				$(this).attr('did','');
				//window.location.href = XY.Util.app.mainPath; 
				//window.location.href = history.go(-1);
			}
		})
		/**
		 * 
		 */
		$('#ok',$('#requestStatus')).click(function(){
			if($(this).attr('did') == 'login'){
				$(this).attr('did','');
				window.location.href = ""+XY.Util.app.contentPath + XY.Util.app.loginPath;
			}else if($(this).attr('did') == 'error'){
				$(this).attr('did','');
				//window.location.href = XY.Util.app.mainPath; 
				//window.location.href = history.go(-1);
			}
		});
	}
	return {
		rootPath : '',
		loginPath : '',
		myTable : {},
		setBodyHeight : setBodyHeight,
		init : function(){
			ajaxStatus();
		}
	}
}