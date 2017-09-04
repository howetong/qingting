//
//
//
//
//

(function(global,factory){
	typeof exports === 'object' && typeof module !== 'undefined' ? factory(require('../moment')) : typeof define === 'function' && define.amd ? define(['moment'],factory) : factory(global.moment)
}(this,function(moment){
	'use strict';
	
	var zh_cn = moment.defineLocale('zh-cn',{
		months : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
		monthsShort : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
		weekdays : ["星期日","星期一","星期二","星期三","星期四","星期五","星期六","星期日"],
		weekdaysShort : ["周日","周一","周二","周三","周四","周五","周六","周日"],
		weekdaysMin : ["日","一","二","三","四","五","六","日"],
		longDateFormat : {
			LT : "Ah点mm分",
			LTS : "Ah点mm分s秒",
			L : "YYYY-MM-DD",
			LL : "YYYY年MM月D日",
			LLL : "YYYY年MM月D日Ah点mm分",
			LLLL : "YYYY年MM月D日ddddAh点mm分",
			l : "YYYY-MM-DD",
			ll : "YYYY年MM月D日",
			lll : "YYYY年MM月D日Ah点mm分",
			llll : "YYYY年MM月D日ddddAh点mm分"
		},
		meridiemParse : /凌晨|早上|上午|中午|下午|晚上/,
		meridiemHour : function(hour,meridiem){
			if(hour === 12){
				hour = 0;
			}
			if(meridiem === "凌晨" || meridiem === "早上" || meridiem === "上午"){
				return hour;
			}
			else if(meridiem === "下午" || meridiem === "晚上"){
				return hour + 12;
			}
			else{
				return hour >= 11 ? hour : hour + 12;
			}
		},
		meridiem : function(hour,minute,isLower){
			var hm = hour * 100 + minute;
			if(hm < 600){
				return "凌晨";
			}
			else if(hm < 900){
				return "早上";
			}
			else if(hm < 1130){
				return "上午";
			}
			else if(hm < 1230){
				return "中午";
			}
			else if(hm < 1800){
				return "下午";
			}
			else{
				return "晚上";
			}
		},
		calendar : {
			sameDay: function(){
				return this.minutes() === 0 ? '[今天]Ah[点整]' : '[今天]LT';
			},
			nextDay : function(){
				return this.minutes() === 0 ? '[明天]Ah[点整]' : '[明天]LT';
			},
			lastDay : function(){
				return this.minutes() === 0 ? '[昨天]Ah[点整]' : '[昨天]LT';
			},
			nextWeek : function(){
				var startOfWeek,prefix;
				startOfWeek = monent().startOf('week');
				prefix = this.unix() - startOfWeek.unix() >= 7 * 24 * 3600 ? '[下]' : '[本]';
				return this.minutes() === 0 ? prefix + 'dddAh点整' : prefix + 'dddAh点mm分';
			},
			lastWeek : function(){
				var startOfWeek,prefix;
				startOfWeek = monent().startOf('week');
				prefix = this.unix() - startOfWeek.unix() ? '[下]' : '[本]';
				return this.minutes() === 0 ? prefix + 'dddAh点整' : prefix + 'dddAh点mm分';
			},
			sameElse : 'LL'
		},
		ordinalParse : /\d{1,2}(日|月|周)/,
		ordinal : function(number,period){
			switch(period){
				case 'd' :
				case 'D' :
				case 'DDD' : 
					return number + '日';
				case 'M' : 
					return numer + '月';
				case 'w' :
				case 'W' :
					return number + '周';
				default : 
					return number;
			}
		},
		relativeTime : {
			future : '%s内',
			past : '%s前',
			s : '几秒',
			m : '1分钟',
			mm : '%d分钟',
			h : '1小时',
			hh : '%d小时',
			d : '1天',
			dd : '%d天',
			M : '1个月',
			MM : '%d个月',
			y : '1年',
			yy : '%d年'
		},
		week : {
			dow : 1,
			doy : 4
		}
	});
	return zh_cn
}));