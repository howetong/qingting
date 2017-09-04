package cn.core.domain;

import javax.persistence.MappedSuperclass;
/**
 * @function 数据字典父类，具有基础数据字典属性
 * @author howeTong
 * @param <T>
 */
@MappedSuperclass
public class BeanDict<T> extends ParentChildrenBean<T> {

	private static final long serialVersionUID = 1L;
	/**
	 * 所属对象关键字
	 */
	private String codeKey;
	
	public BeanDict(){
		super();
	}
	
	public BeanDict(Long id){
		super(id);
	}
	
	public BeanDict(Long id,String name){
		super(id,name);
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}
}
