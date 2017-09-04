package cn.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("all")
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{
	
	private static final Log log = LogFactory.getLog(BeanUtils.class);
	
	private static Set<String> notCopyProperties = new HashSet<>();
	static{
		/*----------*/
		ConvertUtils.register(new DateConverte(), Date.class);
		ConvertUtils.register(new DateConverte(), java.sql.Date.class);
		notCopyProperties.add("class");
		notCopyProperties.add("propName");
		notCopyProperties.add("propValue");
		notCopyProperties.add("currentPage");
		notCopyProperties.add("maxResult");
		notCopyProperties.add("sortColumns");
		notCopyProperties.add("cmd");
		/*----------*/
	}
	public static Map describeAvailableParameter(Object bean) throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		if(bean == null){
			return (new HashMap());
		}
		Map description = new HashMap();
		if(bean instanceof DynaBean){
			DynaProperty[] descriptors = ((DynaBean)bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean,name));
			}
		}else {
			PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if(name.startsWith("$")){
					if(MethodUtils.getAccessibleMethod(clazz,descriptors[i].getReadMethod()) != null){
						description.put(name, PropertyUtils.getNestedProperty(bean, name));
					}
				}
			}
		}
		return description;
	}
	
	public static void copyPropertiesNotNull(Object dest,Object orig) throws IllegalAccessException,InvocationTargetException{
		//验证指定的bean是否存在
		if(dest == null){
			throw new IllegalArgumentException("no destination bean specified");
		}
		if(orig == null){
			throw new IllegalArgumentException("no original bean specified");
		}
		if(log.isDebugEnabled()){
			log.debug("BeanUtils.copyProperties("+dest+","+orig+")");
		}
		//复制属性，并根据需要进行转换
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if(orig instanceof DynaBean){
			DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if(bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)){
					Object value = ((DynaBean)orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		}else if(orig instanceof Map){
			Iterator entries = ((Map)orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String)entry.getKey();
				if(bub.getPropertyUtils().isWriteable(dest, name)){
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		}else {
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if(notCopyProperties.contains(name) || name.startsWith("$")){
					continue;
				}
				if(bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)){
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						if(value == null){
							continue;
						}
						bub.copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// should not happen
					}
				}
			}
		}
	}



	
}
