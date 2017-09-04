package cn.core.utils;

import java.util.Date;

import org.apache.log4j.Logger;


public class LoggerUtils {
	private LoggerUtils(){
		
	}
	
	public static Logger log(Class<?> clz){
		Logger log = Logger.getLogger(clz);
		return log;
	}
	
	public static void info(Class<?> clz,Integer num,Object obj){
		Logger log = Logger.getLogger(clz);
		log.info("-----"+new Date()+"-----");
		log.info(num+"-----"+obj);
		log.info("-----");
	}
	
	public static void debug(Class<?> clz,Integer num,Object obj){
		Logger log = Logger.getLogger(clz);
		log.info("--------"+new Date()+"-----------");
		log.info(num+"--------"+obj);
		log.info("----------------------------");
	}
	
	public static void error(Class<?> clz,Integer num,Object obj){
		Logger log = Logger.getLogger(clz);
		log.info("-----"+new Date()+"-----");
		log.info(num+"-----"+obj);
		log.info("----------");
	}
}
