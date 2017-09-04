package cn.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * @function 重写日期转换
 * @author howe
 *
 */
public class DateConverte implements Converter{

	@SuppressWarnings("rawtypes")
	public Object convert(Class arg0, Object arg1) {
		if (arg0 == null || arg1 == null) {
			return null;
		}
		LoggerUtils.error(getClass(), 23, arg0);
		LoggerUtils.error(getClass(), 24, arg1);
		String p = arg1.toString();
		if (p == null || p.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(p.trim());
		} catch (ParseException e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(p.trim());
			} catch (ParseException e1) {
				return null;
			}
		}
	}

}
