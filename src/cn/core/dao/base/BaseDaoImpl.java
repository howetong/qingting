package cn.core.dao.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import cn.core.domain.BaseBean;
import cn.core.domain.interfaces.IEntityParentChildrenBean;
import cn.core.domain.support.BaseParameter;
import cn.core.domain.support.KeyValueResult;
import cn.core.domain.support.QueryResult;
import cn.core.utils.BeanUtils;
import cn.core.utils.LoggerUtils;

@SuppressWarnings("all")
public class BaseDaoImpl implements IBaseDao {
	private static Map<String, Method> MAP_METHOD = new HashMap<>();
	@Autowired
	private SessionFactory sessionFactory;

	private void error(int num, Object obj) {
		LoggerUtils.error(getClass(), num, obj);
	}

	private void info(int num, Object hql) {
		LoggerUtils.info(getClass(), num, hql);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * hql查询多個對象
	 * 
	 * @param hql
	 *            HQL語句
	 * @param values
	 *            不定参数的object数组
	 * @return List<Object>
	 */
	@Override
	public List<Object> findObjectListByHQL(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}
	/**
	 * hql查询對象字段
	 * 
	 * @param field
	 *            字段名称
	 * @param id
	 *            对象id
	 * @param entitycClass
	 *            数据对象
	 * @return
	 */
	@Override
	public Object findFieldByHQL(String field,Long id,Class<?> entitycClass) {
		String hql = "select " + field +" from "+ entitycClass.getSimpleName()+" o WHERE o.id = ?";
		return getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	/**
	 * hql查询多個對象
	 * 
	 * @param hql
	 *            HQL語句
	 * @param values
	 *            不定参数的object数组
	 * @return <T extends BaseBean> List<T>
	 */
	@Override
	public <T extends BaseBean> List<T> findListByHQL(Class<T> entityClass,String hql,
			Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * hql分页查询 HQL語句
	 * 
	 * @param hql
	 *            下一页
	 * @param pageNo
	 *            每页总数
	 * @param pageSize
	 *            不定参数的object数组
	 * @param values
	 * @return QueryResult<T>
	 */
	@Override
	public <T extends BaseBean> QueryResult<T> findPageListByHQL(Class<T> entityClass,String hql,
			int pageNo, int pageSize, Object... values) {
		QueryResult<T> qr = new QueryResult<>();
		Query query = this.getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		ScrollableResults results = query.scroll();
		results.last();
		qr.setTotalCount(Long.valueOf((results.getRowNumber() + 1)));
		List<T> list = new ArrayList<>();
		list = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		qr.setResultList(list);
		return qr;
	}

	/**
	 * hql查詢單個對象 HQL語句
	 * 
	 * @param hql
	 *            不定参数的object数组
	 * @param values
	 * @return E
	 */
	@Override
	public <T extends BaseBean> T findOneByHQL(Class<T> entityClass,String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * hql查询总数 HQL語句
	 * 
	 * @param hql
	 *            不定参数的object数组
	 * @param values
	 * @return int
	 */
	@Override
	public int findCountByHQL(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list().size();
	}

	/**
	 * 用于前端select,radio,radiobox值 key-value==id-name
	 * 
	 * @param hql
	 * @param obj
	 * @return List<KeyValueResult>
	 */
	@Override
	public <T> List<KeyValueResult> queryKeyValueResultsByProperties(Class<T> entityClass,String hql, Object... obj) {
		List<Object> list = this.findObjectListByHQL(hql, obj);
		List<KeyValueResult> valList = new ArrayList<KeyValueResult>();
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			KeyValueResult kv = new KeyValueResult();
			Object[] objects = (Object[]) list.get(i);
			kv.setId(Long.parseLong(objects[0].toString()));
			kv.setName(objects[1].toString());
			if (objects.length > 2) {
				Object pidstr = objects[2];
				if (pidstr != null) {
					kv.setPid(Long.parseLong(objects[2].toString()));
				}
			}
			valList.add(kv);
		}
		return valList;
	}

	@Override
	public <E extends BaseBean, T extends BaseBean> List<E> finalResultsByCustomerEntity(
			Serializable id, Map<String, String> fieldMap,
			List<Object> parentList, Class<E> entityCustomer,
			Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 用于前端select,radio,radiobox值 key-value==id-name
	 * 
	 * @param id
	 *            排id
	 * @param fieldMap
	 *            Map<field,logic> a = ? and b = ? or c = ?
	 * @param parentList
	 *            参数值
	 * @param entityClass
	 *            查询的对象
	 * @return List<KeyValueResult>
	 */
	public <T extends BaseBean> List<KeyValueResult> finalKeyValueResults(
			Serializable id, Map<String, String> fieldMap,
			List<Object> parentList, Class<T> entityClass) {
		String table = null;
		if (entityClass != null) {
			table = entityClass.getSimpleName();
		}else {
			return null;
		}
		String hql = "select id,name from " + table + " o where 1 = 1 ";
		
		try {
			if (entityClass.newInstance() instanceof IEntityParentChildrenBean) {
				hql = "select id,name,"+ IEntityParentChildrenBean.PARENT_ID_FIELD +" as pid from " + table + " o where 1 = 1 ";
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// 遍历map拼装hql
		for (Entry<String, String> el : fieldMap.entrySet()) {
			String key = el.getKey();
			String value = el.getValue();
			if (value.indexOf("_") > -1) {
				String[] flag = value.split("_");
				if (flag != null && flag.length > 1) {
					hql += flag[0] + " o." + key + " " + flag[1] +" ? ";
				}else
				hql += value + " o." + key + " = ? ";
			}else{
				hql += value + " o." + key + " = ? ";
			}
		}
		if (null != id) {
			hql += " AND o.id != ?";
			parentList.add(id);
		}
		try {
			if (entityClass.newInstance() instanceof IEntityParentChildrenBean) {
				String notc = "select id from " + table + " c where c."+ IEntityParentChildrenBean.PARENT_ID_FIELD +" = ?";
				hql += " AND o.id not in(" + notc + ")";
				parentList.add(id);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Object[] par = new Object[parentList.size()];
		for (int i = 0 ; i < parentList.size();i ++) {
			par[i] = parentList.get(i);
		}
		hql += " order by "+BaseBean.SEQUENCE_FIELD+"  "+ BaseBean.SORTED_DESC+", "+BaseBean.ID_FIELD+" "+BaseBean.SORTED_DESC;
		return this.queryKeyValueResultsByProperties(KeyValueResult.class,hql, par);
	}

	/**
	 * 持久化对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void save(T entity) {
		getSession().persist(entity);
	}

	/**
	 * 新建与修改
	 * @param entity
	 */
	@Override
	public <T extends BaseBean> void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		
	}

	/**
	 * 根据多个id参数删除对象
	 * 
	 * @param id
	 *            多个id，以英文逗号隔开
	 * @return boolean
	 */
	public <T extends BaseBean> boolean deleteByPK(Class<T> entityClass,
			Serializable... id) {
		boolean result = false;
		if ((id != null) && (id.length > 0)) {
			for (int i = 0; i < id.length; i++) {
				T entity = get(id[i], entityClass);
				if (entity != null) {
					getSession().delete(entity);
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * 以HQL的方式，根据多个属性删除对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 */
	public <T extends BaseBean> void deleteByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		if ((propName != null) && (propName.length > 0) && (propValue != null)
				&& (propValue.length > 0)
				&& (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("delete from "
					+ entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			query.executeUpdate();
		}
	}

	/**
	 * 删除对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void delete(T entity) {
		getSession().delete(entity);
	}

	/**
	 * 以HQL的方式，根据单个属性删除对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 */
	public <T extends BaseBean> void deleteByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		deleteByProperties(new String[] { propName },
				new Object[] { propValue }, entityClass);
	}

	/**
	 * 根据多个属性条件更新对象实体多个属性
	 * 
	 * @param conditionName
	 *            WHERE子句条件的属性数组名称
	 * @param conditionValue
	 *            WHERE子句条件的属性数组值
	 * @param propertyName
	 *            UPDATE子句属性数组名称
	 * @param propertyValue
	 *            UPDATE子句属性数组值
	 */
	public <T extends BaseBean> void updateByProperties(String[] conditionName,
			Object[] conditionValue, String[] propertyName,
			Object[] propertyValue, Class<T> entityClass) {
		if ((propertyName != null) && (propertyName.length > 0)
				&& (propertyValue != null) && (propertyValue.length > 0)
				&& (propertyName.length == propertyValue.length)
				&& (conditionValue != null) && (conditionValue.length > 0)) {
			StringBuffer sb = new StringBuffer();
			sb.append("update " + entityClass.getName() + " o set ");
			for (int i = 0; i < propertyName.length; i++) {
				sb.append(propertyName[i] + " = :p_" + propertyName[i] + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where 1=1 ");
			appendQL(sb, conditionName, conditionValue);
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < propertyName.length; i++) {
				query.setParameter("p_" + propertyName[i], propertyValue[i]);
			}
			setParameter(query, conditionName, conditionValue);
			query.executeUpdate();
		} else {
			throw new IllegalArgumentException(
					"Method updateByProperties in BaseDao argument is illegal!");
		}
	}

	/**
	 * 根据单个属性条件更新对象实体多个属性
	 * 
	 * @param conditionName
	 *            WHERE子句条件的属性数组名称
	 * @param conditionValue
	 *            WHERE子句条件的属性数组值
	 * @param propertyName
	 *            UPDATE子句属性名称
	 * @param propertyValue
	 *            UPDATE子句属性值
	 */
	public <T extends BaseBean> void updateByProperties(String[] conditionName,
			Object[] conditionValue, String propertyName, Object propertyValue,
			Class<T> entityClass) {
		updateByProperties(conditionName, conditionValue,
				new String[] { propertyName }, new Object[] { propertyValue },
				entityClass);
	}

	/**
	 * 根据多个属性条件更新对象实体单个属性
	 * 
	 * @param conditionName
	 *            WHERE子句条件的属性名称
	 * @param conditionValue
	 *            WHERE子句条件的属性值
	 * @param propertyName
	 *            UPDATE子句属性数组名称
	 * @param propertyValue
	 *            UPDATE子句属性数组值
	 */
	public <T extends BaseBean> void updateByProperties(String conditionName,
			Object conditionValue, String[] propertyName,
			Object[] propertyValue, Class<T> entityClass) {
		updateByProperties(new String[] { conditionName },
				new Object[] { conditionValue }, propertyName, propertyValue,
				entityClass);
	}

	/**
	 * 根据单个属性条件更新对象实体单个属性
	 * 
	 * @param conditionName
	 *            WHERE子句条件的属性名称
	 * @param conditionValue
	 *            WHERE子句条件的属性值
	 * @param propertyName
	 *            UPDATE子句属性名称
	 * @param propertyValue
	 *            UPDATE子句属性值
	 */
	public <T extends BaseBean> void updateByProperties(String conditionName,
			Object conditionValue, String propertyName, Object propertyValue,
			Class<T> entityClass) {
		updateByProperties(new String[] { conditionName },
				new Object[] { conditionValue }, new String[] { propertyName },
				new Object[] { propertyValue }, entityClass);
	}

	/**
	 * 根据给定的Detached对象标识符更新对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void update(T entity) {
		getSession().update(entity);
	}

	/**
	 * 先删除再插入去更新对象实体
	 * 
	 * @param entity
	 *            待更新的对象实体
	 * @param oldId
	 *            已存在的对象实体主键
	 */
	public <T extends BaseBean> void update(T entity, Class<T> oldEntityClass,
			Serializable oldId) {
		deleteByPK(oldEntityClass, new Serializable[] { oldId });
		save(entity);
	}

	/**
	 * 合并给定的对象实体状态到当前的持久化上下文
	 * 
	 * @param entity
	 *            给定的对象实体
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T merge(T entity) {
		return (T) getSession().merge(entity);
	}

	/**
	 * 根据属性数组和排序条件获取单个对象实体
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param sortedCondition
	 *            排序条件
	 * @param entityClass
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T getByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		if ((propName != null) && (propName.length > 0) && (propValue != null)
				&& (propValue.length > 0)
				&& (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("select o from "
					+ entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
				sb.append(" order by ");
				for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append((String) e.getKey() + " " + (String) e.getValue()
							+ ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			List<T> list = query.list();
			if ((list != null) && (((List) list).size() > 0)) {
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据ID立即加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T get(Serializable id, Class<T> entityClass) {
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * 根据ID立即加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T get(String id, Class<T> entityClass) {
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * 根据ID延迟加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T load(Serializable id, Class<T> entityClass) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 根据ID延迟加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T load(String id, Class<T> entityClass) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 根据属性数组获取单个对象实体
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T getByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		return getByProperties(propName, propValue, null, entityClass);
	}

	/**
	 * 根据属性获取单个对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T getByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		return getByProperties(new String[] { propName },
				new Object[] { propValue }, entityClass);
	}

	/**
	 * 根据属性和排序条件获取单个对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @param sortedCondition
	 *            排序条件
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T getByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return getByProperties(new String[] { propName },
				new Object[] { propValue }, sortedCondition, entityClass);
	}

	/**
	 * 根据属性、排序条件和要返回的记录数目获取对象实体列表
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param sortedCondition
	 *            排序条件
	 * @param top
	 *            要返回的记录数目
	 * @param entityClass
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Integer top, Class<T> entityClass) {
		if ((propName != null) && (propValue != null)
				&& (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("select o from "
					+ entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
				sb.append(" order by ");
				for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append((String) e.getKey() + " " + (String) e.getValue()
							+ ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			if (top != null) {
				query.setFirstResult(0);
				query.setMaxResults(top.intValue());
			}
			return query.list();
		}
		return null;
	}

	/**
	 * 根据属性和要返回的记录数目获取对象实体列表
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param top
	 *            要返回的记录数目
	 * @param entityClass
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Integer top, Class<T> entityClass) {
		return queryByProperties(propName, propValue, null, top, entityClass);
	}

	/**
	 * 根据属性和排序条件获取对象实体列表
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param sortedCondition
	 *            排序条件
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return queryByProperties(propName, propValue, sortedCondition, null,
				entityClass);
	}

	/**
	 * 根据属性、排序条件和要返回的记录数目获取对象实体列表
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @param sortedCondition
	 *            排序条件
	 * @param top
	 *            要返回的记录数目
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition, Integer top,
			Class<T> entityClass) {
		return queryByProperties(new String[] { propName },
				new Object[] { propValue }, sortedCondition, top, entityClass);
	}

	/**
	 * 根据属性和排序条件获取对象实体列表
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @param sortedCondition
	 *            排序条件
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return queryByProperties(new String[] { propName },
				new Object[] { propValue }, sortedCondition, null, entityClass);
	}

	/**
	 * 根据属性和要返回的记录数目获取对象实体列表
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @param top
	 *            要返回的记录数目
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Integer top, Class<T> entityClass) {
		return queryByProperties(new String[] { propName },
				new Object[] { propValue }, null, top, entityClass);
	}

	/**
	 * 根据属性获取对象实体列表
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		return queryByProperties(propName, propValue, null, null, entityClass);
	}

	/**
	 * 根据属性获取对象实体列表
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		return queryByProperties(new String[] { propName },
				new Object[] { propValue }, null, null, entityClass);
	}

	/**
	 * 查询出对象实体的所有数目
	 * 
	 * @return 返回对象实体所有数目
	 */
	public <T extends BaseBean> Long countAll(Class<T> entityClass) {
		return (Long) getSession().createQuery(
				"select count(*) from " + entityClass.getName()).uniqueResult();
	}
	public <T extends BaseBean> void refresh(T entity){
		getSession().refresh(entity);
	}
	/**
	 * 刷新会话
	 */
	@Override
	public <T extends BaseBean> void flush() {
		getSession().flush();
	}

	/**
	 * 彻底清除会话
	 */
	public void clear() {
		getSession().clear();
	}

	/**
	 * 从会话缓存中删除此对象实体
	 * 
	 * @param entity
	 *            待删除的对象实体
	 */
	public <T extends BaseBean> void evict(T entity) {
		getSession().evict(entity);
	}

	/**
	 * 根据排序条件和要返回的记录数目查询出对象实体列表
	 * 
	 * @param sortedCondition
	 *            排序条件
	 * @param top
	 *            要返回的记录数目
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQueryAll(
			Map<String, String> sortedCondition, Integer top,
			Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
			for (Iterator<String> it = sortedCondition.keySet().iterator(); it
					.hasNext();) {
				String pm = (String) it.next();
				if ("DESC".equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.desc(pm));
				} else if ("ASC".equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.asc(pm));
				}
			}
		}
		if (top != null) {
			criteria.setMaxResults(top.intValue());
			criteria.setFirstResult(0);
		}
		return criteria.list();
	}

	/**
	 * 查询出所有的对象实体列表
	 * 
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQueryAll(Class<T> entityClass) {
		return doQueryAll(null, null, entityClass);
	}

	/**
	 * 根据要返回的记录数目查询出对象实体列表
	 * 
	 * @param top
	 *            要返回的记录数目
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQueryAll(Integer top,
			Class<T> entityClass) {
		return doQueryAll(null, top, entityClass);
	}

	/**
	 * 根据各种查询条件返回对象实体数目
	 * 
	 * @param param
	 *            各种查询条件
	 * @return 返回对象实体数目
	 */
	public <T extends BaseBean> Long doCount(BaseParameter param,
			Class<T> entityClass) {
		if (param == null) {
			return null;
		}
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param, entityClass);
		try {
			criteria.setProjection(Projections.rowCount());
			return Long.valueOf(((Number) criteria.uniqueResult()).longValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据各种查询条件返回对象实体列表
	 * 
	 * @param param
	 *            各种查询条件
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQuery(BaseParameter param,
			Class<T> entityClass) {
		if (param == null) {
			return null;
		}
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param, entityClass);
		try {
			if ((param.getSortedConditions() != null)
					&& (param.getSortedConditions().size() > 0)) {
				Map<String, String> map = param.getSortedConditions();
				for (Iterator<String> it = param.getSortedConditions().keySet()
						.iterator(); it.hasNext();) {
					String pm = (String) it.next();
					if ("DESC".equals(map.get(pm))) {
						criteria.addOrder(Order.desc(pm));
					} else if ("ASC".equals(map.get(pm))) {
						criteria.addOrder(Order.asc(pm));
					}
				}
			}
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据各种查询条件返回分页列表
	 * 
	 * @param param
	 *            各种查询条件
	 * @return 返回分页列表
	 */
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter param, Class<T> entityClass) {
		return doPaginationQuery(param, true, entityClass);
	}

	/**
	 * 根据各种查询条件返回分页列表
	 * 
	 * @param param
	 *            各种查询条件
	 * @param bool
	 *            默认为true；如果为false，增加属性是否为空等查询条件
	 * @param entityClass
	 * @return 返回分页列表
	 */
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter param, boolean bool, Class<T> entityClass) {
		if (param == null) {
			return null;
		}
		Criteria criteria = getSession().createCriteria(entityClass);
		if (bool) {
			processQuery(criteria, param, entityClass);
		} else {
			extendprocessQuery(criteria, param, entityClass);
		}
		try {
			QueryResult<T> qr = new QueryResult();
			criteria.setProjection(Projections.rowCount());
			qr.setTotalCount(Long.valueOf(((Number) criteria.uniqueResult())
					.longValue()));
			if (qr.getTotalCount().longValue() > 0L) {
				if ((param.getSortedConditions() != null)
						&& (param.getSortedConditions().size() > 0)) {
					Map<String, String> map = param.getSortedConditions();
					for (Iterator<String> it = param.getSortedConditions()
							.keySet().iterator(); it.hasNext();) {
						String pm = (String) it.next();
						if ("DESC".equalsIgnoreCase((String) map.get(pm))) {
							criteria.addOrder(Order.desc(pm));
						} else if ("ASC".equalsIgnoreCase((String) map.get(pm))) {
							criteria.addOrder(Order.asc(pm));
						}
					}
				}
				criteria.setProjection(null);
				int first = param.getFirstResult();
				int max = param.getRows();
				if (first != -1 && max != -1) {
					// 分页,开始
					criteria.setFirstResult(first);
					// 分页,结束
					criteria.setMaxResults(max);
				}
				qr.setResultList(criteria.list());
			} else {
				qr.setResultList(new ArrayList());
			}
			return qr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (((value instanceof Object[]))
					|| ((value instanceof Collection))) {
				Object[] arraySerializable = (Object[]) value;
				if ((arraySerializable != null)
						&& (arraySerializable.length > 0)) {
					sb.append(" and o." + name + " in (:"
							+ name.replace(".", "") + ")");
				}
			} else if (value == null) {
				sb.append(" and o." + name + " is null ");
			} else {
				sb.append(" and o." + name + "=:" + name.replace(".", ""));
			}
		}
	}

	private void setParameter(Query query, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value != null) {
				if ((value instanceof Object[])) {
					query.setParameterList(name.replace(".", ""),
							(Object[]) value);
				} else if ((value instanceof Collection)) {
					query.setParameterList(name.replace(".", ""),
							(Collection) value);
				} else {
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}

	protected void buildSorted(BaseParameter param, StringBuffer hql) {
		if ((param.getSortedConditions() != null)
				&& (param.getSortedConditions().size() > 0)) {
			hql.append(" order by ");
			Map<String, String> sorted = param.getSortedConditions();
			for (Iterator<String> it = sorted.keySet().iterator(); it.hasNext();) {
				String col = (String) it.next();
				hql.append(col + " " + (String) sorted.get(col) + ",");
			}
			hql.deleteCharAt(hql.length() - 1);
		}
	}

	private String transferColumn(String queryCondition) {
		return queryCondition.substring(queryCondition.indexOf('_', 1) + 1);
	}

	protected void setParameter(Map<String, Object> mapParameter, Query query) {
		for (Iterator<String> it = mapParameter.keySet().iterator(); it
				.hasNext();) {
			String parameterName = (String) it.next();
			Object value = mapParameter.get(parameterName);
			query.setParameter(parameterName, value);
		}
	}

	protected Map handlerConditions(BaseParameter param) throws Exception {
		Map staticConditions = BeanUtils.describe(param);
		Map<String, Object> dynamicConditions = param
				.getQueryDynamicConditions();
		if (dynamicConditions.size() > 0) {
			for (Iterator it = staticConditions.keySet().iterator(); it
					.hasNext();) {
				String key = (String) it.next();
				Object value = staticConditions.get(key);
				if ((key.startsWith("$")) && (value != null)
						&& (!"".equals(value))) {
					dynamicConditions.put(key, value);
				}
			}
			staticConditions = dynamicConditions;
		}
		return staticConditions;
	}

	private Method getMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = { String.class, Object.class };
			Class[] likeParamType = { String.class, String.class,
					MatchMode.class };
			Class[] isNullType = { String.class };
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (Method) MAP_METHOD.get(name);
	}

	private Method getExtendMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = { String.class, Object.class };
			Class[] likeParamType = { String.class, String.class,
					MatchMode.class };
			Class[] isNullType = { String.class };
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else if (!"IN".equals(name.toUpperCase())) {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (Method) MAP_METHOD.get(name);
	}

	private String getOpt(String value) {
		return value.substring(0, value.indexOf('_', 1)).substring(1);
	}

	private String getPropName(String value) {
		return value.substring(value.indexOf('_', 1) + 1);
	}

	private <T extends BaseBean> void processQuery(Criteria criteria,
			BaseParameter param, Class<T> entityClass) {
		try {
			Map<String, Object> staticConditionMap = cn.core.utils.BeanUtils
					.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param
					.getQueryDynamicConditions();
			Disjunction disjunction = Restrictions.disjunction();
			String prop;
			if ((staticConditionMap != null) && (staticConditionMap.size() > 0)) {
				for (Map.Entry<String, Object> e : staticConditionMap
						.entrySet()) {
					Object value = e.getValue();
					if ((value != null)
							&& ((!(value instanceof String)) || (!""
									.equals((String) value)))) {
						prop = getPropName((String) e.getKey());
						String methodName = getOpt((String) e.getKey());
						Method m = getMethod(methodName);
						if ("like".equals(methodName)) {
							if (param.getFlag().equals("OR")) {
								criteria.add(disjunction.add((Criterion) m
										.invoke(Restrictions.class,
												new Object[] { prop, value,
														MatchMode.ANYWHERE })));
							} else {
								criteria.add((Criterion) m.invoke(
										Restrictions.class,
										new Object[] { prop, value,
												MatchMode.ANYWHERE }));
							}
						} else if (("isNull".equals(methodName))
								&& ((value instanceof Boolean))) {
							if (((Boolean) value).booleanValue()) {
								if (param.getFlag().equals("OR")) {
									criteria.add(disjunction.add(Restrictions
											.isNull(prop)));
								} else {
									criteria.add(Restrictions.isNull(prop));
								}
							} else if (param.getFlag().equals("OR")) {
								criteria.add(disjunction.add(Restrictions
										.isNotNull(prop)));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else if (param.getFlag().equals("OR")) {
							criteria.add(disjunction.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop,
											value })));
						} else {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop,
											value }));
						}
					}
				}
			}
			if ((dynamicConditionMap != null)
					&& (dynamicConditionMap.size() > 0)) {
				Object bean = entityClass.newInstance();
				Object map = new HashMap();
				for (Map.Entry<String, Object> e : dynamicConditionMap
						.entrySet()) {
					((Map) map).put(getPropName((String) e.getKey()),
							e.getValue());
				}
				org.apache.commons.beanutils.BeanUtils
						.populate(bean, (Map) map);
				for (Map.Entry<String, Object> e : dynamicConditionMap
						.entrySet()) {
					String pn = (String) e.getKey();
					String prop1 = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop1);
					if ((value != null)
							&& ((!(value instanceof String)) || (!""
									.equals((String) value)))) {
						if ("like".equals(methodName)) {
							if (param.getFlag().equals("OR")) {
								criteria.add(disjunction.add((Criterion) m
										.invoke(Restrictions.class,
												new Object[] { prop1, value,
														MatchMode.ANYWHERE })));
							} else {
								criteria.add((Criterion) m.invoke(
										Restrictions.class, new Object[] {
												prop1, value,
												MatchMode.ANYWHERE }));
							}
						} else if (("isNull".equals(methodName))
								&& ((value instanceof Boolean))) {
							if (((Boolean) value).booleanValue()) {
								if (param.getFlag().equals("OR")) {
									criteria.add(disjunction.add(Restrictions
											.isNull(prop1)));
								} else {
									criteria.add(Restrictions.isNull(prop1));
								}
							} else if (param.getFlag().equals("OR")) {
								criteria.add(disjunction.add(Restrictions
										.isNotNull(prop1)));
							} else {
								criteria.add(Restrictions.isNotNull(prop1));
							}
						} else if (param.getFlag().equals("OR")) {
							criteria.add(disjunction.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop1,
											value })));
						} else {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop1,
											value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private <T extends BaseBean> void extendprocessQuery(Criteria criteria,
			BaseParameter param, Class<?> entityClass) {
		try {
			Map<String, Object> staticConditionMap = cn.core.utils.BeanUtils
					.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param
					.getQueryDynamicConditions();
			String prop;
			if ((staticConditionMap != null) && (staticConditionMap.size() > 0)) {
				for (Map.Entry<String, Object> e : staticConditionMap
						.entrySet()) {
					Object value = e.getValue();
					if ((value != null)
							&& ((!(value instanceof String)) || (!""
									.equals((String) value)))) {
						prop = getPropName((String) e.getKey());
						String methodName = getOpt((String) e.getKey());
						Method m = getExtendMethod(methodName);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop,
											value, MatchMode.ANYWHERE }));
						} else if (("isNull".equals(methodName))
								&& ((value instanceof Boolean))) {
							if (((Boolean) value).booleanValue()) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else if ((value != null)
								&& ((value instanceof Object[]))
								&& ("IN".equals(methodName.toUpperCase()))) {
							Object[] obj = (Object[]) value;
							criteria.add(Restrictions.in(prop, obj));
						} else {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop,
											value }));
						}
					}
				}
			}
			if ((dynamicConditionMap != null)
					&& (dynamicConditionMap.size() > 0)) {
				Object bean = entityClass.newInstance();
				Object map = new HashMap();
				for (Map.Entry<String, Object> e : dynamicConditionMap
						.entrySet()) {
					((Map) map).put(getPropName((String) e.getKey()),
							e.getValue());
				}
				org.apache.commons.beanutils.BeanUtils
						.populate(bean, (Map) map);
				for (Map.Entry<String, Object> e : dynamicConditionMap
						.entrySet()) {
					String pn = (String) e.getKey();
					String prop1 = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					Object value = PropertyUtils.getNestedProperty(bean, prop1);
					if ((value != null)
							&& ((!(value instanceof String)) || (!""
									.equals((String) value)))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop1,
											value, MatchMode.ANYWHERE }));
						} else if (("isNull".equals(methodName))
								&& ((value instanceof Boolean))) {
							if (((Boolean) value).booleanValue()) {
								criteria.add(Restrictions.isNull(prop1));
							} else {
								criteria.add(Restrictions.isNotNull(prop1));
							}
						} else {
							criteria.add((Criterion) m.invoke(
									Restrictions.class, new Object[] { prop1,
											value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
