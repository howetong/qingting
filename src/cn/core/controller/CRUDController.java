package cn.core.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.MappedSuperclass;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.domain.BaseBean;
import cn.core.domain.interfaces.IEntityParentChildrenBean;
import cn.core.domain.interfaces.IEntityUserNewAndUpdate;
import cn.core.domain.support.BaseParameter;
import cn.core.domain.support.ExpandBaseParameter;
import cn.core.domain.support.KeyValueResult;
import cn.core.domain.support.PageView;
import cn.core.domain.support.QueryResult;
import cn.core.utils.BeanUtils;
@SuppressWarnings("all")
@MappedSuperclass
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
public class CRUDController<E extends BaseBean> extends BaseController<E>{
	
	protected HashMap<String, String> sortedConditionsMap = new LinkedHashMap<String, String>();
	/**
	 * 数据列表方法，可复杂查询
	 * @param entity 
	 * @param request
	 * @param response
	 * @param sEcho
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public PageView<E> getList(@ModelAttribute E entity) {
		beforeList(entity);
		ExpandBaseParameter baseParameter = entity;
		//阻止前端提交删除查询条件
		baseParameter.set$eq_delStatus(false);
		BaseParameter parameter = baseParameter;
		QueryResult<E> queryResult = this.service.doPaginationQuery(parameter,entityClass);
		/**
		 * 视图界面对象，包含分页，默认10条数据
		 */
		PageView<E> pageViewDate = new PageView<E>(entity.getRows(),entity.getPage());
		pageViewDate.setRows(queryResult.getResultList());
		pageViewDate.setTotal(queryResult.getTotalCount());
		afterList(queryResult.getResultList());
		return pageViewDate;
	}
	
	/**
	 * 新建和保存,通过前端提交的cmd参数(edit&new)的区别.
	 * 
	 * @param entity
	 * @return E
	 */
	@RequestMapping(value = { "/save" }, produces = { "application/json;charset=utf-8" }, method = { RequestMethod.POST })
	@ResponseBody
	public E doSave(@RequestBody E entity) {
		BaseBean baseBean = (BaseBean) entity;
		//如果名称为空，则保存失败
		if (baseBean != null && baseBean.getName() == null) {
			entity.setSuccess(false);
			entity.setMessage("{name}为空!保存失败");
			return entity;
		}
		//通用预处理。如设置创建者和更新者等。
		setSaveOrUpdateEntity(entity);
		Date now = new Date();
		//if 前端编辑操作之后的保存；else if 前端新建操作之后的保存
		if (CMD_EDIT.equals(baseBean.getCmd())) {
			if (entity instanceof BaseBean) {
				// 判断id
				if (idAsNot((BaseBean) entity)) {
					throw new NullPointerException("id as null");
				}
			}
			// 获取数据库原始数据
			E dest = this.service.get(entityClass, baseBean.getId());
			// 重新加载对象,保证正常修改
			this.service.refresh(dest);
			//数据更新预处理
			beforeSaveUpdate(entity, dest);
			try {
				// 复制不为空的属性。对于空属性应该在更新预处理函数中进行处理
				BeanUtils.copyPropertiesNotNull(dest, entity);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			BaseBean bean = (BaseBean) dest;
			//设置更新时间
			bean.setUpdateTime(now);
			this.service.refresh(entity);
			this.service.update(dest);
			//保存之后的处理
			afterSaveUpdate(entity, dest);
		}else if (CMD_NEW.equals(baseBean.getCmd())) {
			BaseBean bean = (BaseBean) entity;
			bean.setCreateTime(now);
			//如果新建的数据id异常，则设置为空
			if (bean.getId() != null && bean.getId() <= 0) {
				baseBean.setId(null);
			}
			//新建操作保存之前的预处理
			beforeSaveNew(entity);
			this.service.save(entity);
			//新建操作保存之后的预处理
			afterSaveNew(entity);
		}
		entity.setSuccess(true);
		return entity;
	}
	
	
	/**
	 * 
	 * @description 获取要编辑的实体对象
	 * @param 
	 * @return E
	 */
	@RequestMapping(value = {"/edit"},produces = {"application/json;charset=utf-8"},method = {RequestMethod.POST})
	@ResponseBody
	public E getEdit(@RequestParam(value = "id") Long id){
		// 取出非物理删除的实体
		E entity = this.service.getByProperties(new String[]{this.delField,this.idField}, new Object[]{false, id} , entityClass);
		if (entity != null) {
			beforeEdit(entity);
		}
		return entity;
	}
	
	/**
	 * 
	 * @description 获得id与name的键值对象，用于前端下拉列表内容展示
	 * @param 
	 * @return List<KeyValueResult>
	 */
	@RequestMapping(value = { "/selectlist" }, produces = { "application/json;charset=utf-8" }, method = { RequestMethod.POST })
	@ResponseBody
	public List<KeyValueResult> getSelectList(Boolean isParent, Long id,Long parentid) {
		//属性和逻辑map
		Map<String, String> fieldMap = new LinkedHashMap<>();
		//属性对应的值列表
		List<Object> valueList = new ArrayList<>();
		fieldMap.put(BaseBean.DEL_STATUS_FIELD, " AND ");
		valueList.add(false);
		if (isParent != null) {
			fieldMap.put(IEntityParentChildrenBean.ASPARENT_FIELD, " AND ");
			valueList.add(isParent);
		}
		if (parentid != null) {
			fieldMap.put(IEntityParentChildrenBean.PARENT_ID_FIELD, " AND ");
			valueList.add(parentid);
		}
		List<KeyValueResult> list = this.service.finalKeyValueResults(id,fieldMap, valueList, entityClass);
		return list;
	}
	
	/**
	 * 保存和修改数据时,自动设置部分值
	 * 
	 * @param entity
	 */
	protected void setSaveOrUpdateEntity(E entity) {
		BaseBean baseBean = (BaseBean) entity;
		if (entity instanceof IEntityUserNewAndUpdate) {
			try{
				BeanUtils.setProperty(entity,
						IEntityUserNewAndUpdate.CREATE_USER,
						getCurrentSysUser());
				BeanUtils.setProperty(entity,
						IEntityUserNewAndUpdate.UPDATE_USER,
						getCurrentSysUser());
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected boolean idAsNot(BaseBean bean){
		if(bean != null && bean.getId() !=null && !(bean.getId()).equals("")){
			return false;
		}
		return true;
	}
	
	protected void getService(){
		
	}
	
	protected void beforeList(E list){
		
	}
	
	protected void afterList(List<E> list){
		
	}
	
	protected void beforeAllList(E list){
		
	}
	
	protected void afterAllList(List<E> list){
		
	}

	protected void beforeSaveNew(E entity){
		
	}
	
	protected void afterSaveNew(E entity){
		
	}
	
	protected void beforeSaveUpdate(E entity, E dest) {
	}
	
	protected void afterSaveUpdate(E entity, E dest) {
	}
	
	protected void afterRemove(Long[] ids) {
	}

	protected void beforeRemove(Long[] ids) {
	}
	
	protected void afterDelete(Long[] ids) {
	}
	
	protected void beforeDelete(Long[] ids) {
	}
	
	protected void beforeEdit(E entity) {
	}

}
