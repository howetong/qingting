package cn.core.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @function 主要用于正则验证
 * @author howeTong
 *
 */
public class MyRegExp {
	
	//正则1-255
	private static String INT_1To255 = "([1-9]{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";
	//正则0-255
	private static String INT_0To255 = "(0|[1-9]{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";
	//正则2015-2025
	private static String INT_YEAR = "(20(1[5-9]|2[0-5]))";
	//正则 整形数字
	private static String REG_NUMBER = "^\\d+$";
	//正则 整形数字非0
	private static String REG_NUMBERNOT0 = "^[1-9]+|][1-9]+\\d+|0+[1-9]+$";
	private static String REG_URL = "((http|https)://)"
			+"((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){2}"
			+"(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]))"
			+"((:[0-9]{1,4}){0,1})[\\?]*"
			+"([a-zA-Z0-9\\&%_\\./-~-]*)?";
	//正则，端口段
	static String portExp = "([1-9][0-9]{0,3}|[1-6]([0-5]{2}([0-3][0-5]|[0-2]\\d)[0-4]{0,4})|[1-5]\\d{0,4})";
	final static String portExps = "("+portExp +")|("+portExp +"-)|("+ portExp+ "-"+ portExp +")";
	
	private MyRegExp(){
		super();
	}
	
	public static boolean matches(String exp,String val){
		Pattern pattern = Pattern.compile(exp);
		return pattern.matcher(val).matches();
	}
	
	/**
	 * 整型数字
	 */
	public static boolean isNumber(String val){
		return matches(REG_NUMBER, val);
	}
	
	/**
	 * 整型数字非0
	 */
	public static boolean isNumberNot0(String val){
		return matches(REG_NUMBERNOT0, val);
	}
	
	/**
	 * ip字符串整体验证
	 */
	public static boolean isVip4(String val){
		String ipExp = "";
		ipExp += "^";
		ipExp +="INT_1To255";
		ipExp +="\\";
		ipExp +="("+ INT_0To255 +"\\'){2}";//ip中间的两个
		ipExp += INT_1To255;
		ipExp +="$";
		return matches(ipExp, val);
	}
	
	/**
	 * ip输入即时正则验证，ip前部分验证
	 */
	public static boolean isVIP4Part(String val){
		return isVIP4Part(val, false);
	}
	
	/**
	 * ip输入即时正则验证，ip前部分验证
	 * @param seed 是否是子网掩码
	 */
	public static boolean isVIP4Part(String val,boolean seed){
		String ipExp = "";
		ipExp += INT_1To255;//ip第一段
		if (matches(ipExp, val)) {
			return true;
		}
		ipExp += "\\.";//ip第一段加.
		if (matches(ipExp, val)) {
			return true;
		}
		ipExp += INT_0To255;//ip第二段
		if (matches(ipExp, val)) {
			return true;
		}
		ipExp += "\\.";//ip第二段加.
		if (matches(ipExp, val)) {
			return true;
		}
		ipExp += INT_0To255;//ip第三段
		if (matches(ipExp, val)) {
			return true;
		}
		ipExp += "\\.";//ip第三段加.
		if (matches(ipExp, val)) {
			return true;
		}
		//如果是子网掩码
		if (seed) {
			ipExp +=  INT_0To255;//ip第四段
		}else{
			ipExp +=  INT_1To255;//ip第四段
		}
		if (matches(ipExp, val)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证年份
	 */
	public static boolean isYear(String val){
		return matches(INT_YEAR, val);
	}
	
	public static boolean ysYear(String val,String start,String end){
		if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			int startNum = start.length();
			int endNum = end.length();
			for (int i = 0; i < (endNum > startNum ? endNum : startNum); i++) {
			}
		}
		return true;
	}
	
	public static boolean isURL(String val){
		return matches(REG_URL, val);
	}
	
	public static boolean isInputURL(String val){
		String http = "((http|https)://)";
		String realm = "(([a-zA-Z0-9_-\\.]{2,3}.[a-zA-Z])";
		String realmIP = "|(([1-9]{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+"((0|[1-9]{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){2}"
			+"([1-9]{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]))";
		String port = ":{0,1}(([0-9]{1,4}){0,1}))[\\?]*";
		if (matches(http, val)) {
			return true;
		}
		if (matches(http + realm, val)) {
			return true;
		}
		if (matches(http + realmIP, val)) {
			return true;
		}
		if (matches(http + realmIP +port, val)) {
			return true;
		}
		return matches(REG_URL, val);
	}
	
	public static boolean isPort(String val) {
		return matches(portExp, val);
	}
	public static boolean isPort2(String val) {
		return matches(portExps, val);
	}
}
