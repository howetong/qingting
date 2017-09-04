package cn.core.domain.interfaces;

import java.util.List;

import cn.core.domain.BaseBean;

public interface IEntityParentChildrenBean<T extends BaseBean> {
	/**
	 * 父类元素字段,T型
	 */
	public static final String PARENT_FIELD = "parent";
	/**
	 * 父类元素id字段,Long型
	 */
	public static final String PARENT_ID_FIELD = "parent.id";
	/**
	 * 枝干,Boolean型
	 */
	public static final String ASPARENT_FIELD = "asParent";
	/**
	 * 叶子,Boolean型
	 */
	public static final String LEAF_FIELD = "leaf";
	/**
	 * 子类字段,List<T>型
	 */
	public static final String CHILDREN_FIELD = "children";

	public T getParent();

	public void setParent(T parent);

	public List<T> getChildren();

	public void setChildren(List<T> children);
}
