package cn.core.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import cn.core.domain.BeanDict;
@Entity
@Table(name="Sys_Dict")
@Cacheable(value="Sys_Dict_cache")
public class SysDict extends BeanDict<SysDict>{
	
	private static final long serialVersionUID = 1L;
	
	public SysDict(){
		super();
	}
	
	public SysDict(Long id){
		super(id);
	}
	
	public SysDict(Long id,String name){
		super(id,name);
	}
}
