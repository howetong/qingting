package cn.core.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class StringTest {
	private int a;
	public void func(StringBuffer str){
		str = new StringBuffer("nihao");
		System.out.println(str.toString());
	}
	
	public static void main(String[] args){
		StringBuffer stringBuffer = new StringBuffer("tonghao");
		StringTest a = new StringTest();
		a.func(stringBuffer);
		System.out.println(stringBuffer);
		Class clazz = StringTest.class;
		Field[] fields = clazz.getFields();
		for(Field filed : fields){
			//System.out.println(filed.getName());
		}
		
		FruitName annotation = Apple.class.getAnnotation(FruitName.class);
		System.out.println(annotation.toString());
	}
	
	

}

	/*public class StringTest{
	    public static void main(String[] args) {
	        Object o = new Object();
	        System.out.println(o);
	        change(o);
	        System.out.println(o);
	    }
	    public static void change(Object o){
	        o = new Object();
	    }*/
