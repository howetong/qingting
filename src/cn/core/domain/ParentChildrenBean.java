package cn.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.springframework.cache.annotation.Cacheable;
import com.alibaba.fastjson.annotation.JSONField;
@MappedSuperclass
public abstract class ParentChildrenBean<T> extends ResourceBean<T>{

	private static final long serialVersionUID = 1L;
	/**
	 * 父类元素字段
	 */
	public static final String PARENT_FIELD = "parent";
	/**
	 * 枝干
	 */
	public static final String ASPARENT_FIELD = "asParent";
	/**
	 * 叶子
	 */
	public static final String LEAF_FIELD = "leaf";
	/**
	 * 子类字段
	 */
	public static final String CHILDREN_FIELD = "children";
	/**
	 * 父类
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	@JSONField(serialize=false)
	private T parent;
	/**
	 * 子类
	 */
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private List<T> children = new ArrayList<>();
	
	public ParentChildrenBean() {
		super();
	}
	
	public ParentChildrenBean(Long id) {
		super(id);
	}
	
	public ParentChildrenBean(Long id,String name) {
		super(id,name);
	}

	@Cacheable(value="parent_cache")
	public T getparent() {
		return parent;
	}

	public void setparent(T parent) {
		this.parent = parent;
	}

	@Cacheable(value="children_cache")
	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	
	
}
