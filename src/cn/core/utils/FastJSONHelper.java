package cn.core.utils;

import com.alibaba.fastjson.JSON;

public class FastJSONHelper<T> {
	/**
	 * 将java类型的对象转换成JSON格式的字符串
	 */
	public static<T> String serialize(T object){
		return JSON.toJSONString(object,true);
	}
}
