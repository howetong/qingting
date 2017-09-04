package cn.core.utils.springmvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @function 帮助构建属性编辑器
 * @author howeTong
 *
 */
public class CustomDateEditor extends PropertyEditorSupport{
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final DateFormat[] ACCEPT_DATE_FORMAT = {new SimpleDateFormat(DEFAULT_DATETIME_FORMAT),new SimpleDateFormat(DEFAULT_DATE_FORMAT)};
	/**
	 * 
	 */
	public void setAsText(String text) throws IllegalArgumentException{
		if(text == null || text.trim().equals("")){
			setValue(null);
		}
		for(DateFormat format : ACCEPT_DATE_FORMAT){
			try {
				setValue(format.parse(text));
				return;
			} catch (ParseException e) {
				continue;
			}catch (RuntimeException e) {
				continue;
			}
		}
	}
	
	public String getAsText(){
		return (String) getValue();
	}
}
