package cn.core.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.core.domain.BaseBean;
import cn.core.domain.support.BaseParameter;
import cn.core.domain.support.KeyValueResult;
import cn.core.domain.support.QueryResult;

public interface IBaseDao {

	/**
	 * hql查询多個對象
	 * 
	 * @param hql
	 *            HQL語句
	 * @param values
	 *            不定参数的object数组
	 * @return List<Object>
	 */
	public List<Object> findObjectListByHQL(String hql, Object... values);

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
	public Object findFieldByHQL(String field, Long id, Class<?> entitycClass);

	/**
	 * hql查询多個對象
	 * 
	 * @param hql
	 *            HQL語句
	 * @param values
	 *            不定参数的object数组
	 * @return <T extends BaseBean> List<T>
	 */
	public <T extends BaseBean> List<T> findListByHQL(Class<T> entityClass,
			String hql, Object... values);

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
	public <T extends BaseBean> QueryResult<T> findPageListByHQL(
			Class<T> entityClass, String hql, int pageNo, int pageSize,
			Object... values);

	/**
	 * hql查詢單個對象 HQL語句
	 * 
	 * @param hql
	 *            不定参数的object数组
	 * @param values
	 * @return E
	 */
	public <T extends BaseBean> T findOneByHQL(Class<T> entityClass,
			String hql, Object... values);

	/**
	 * hql查询总数 HQL語句
	 * 
	 * @param hql
	 *            不定参数的object数组
	 * @param values
	 * @return int
	 */
	public <T extends BaseBean> int findCountByHQL(String hql, Object... values);

	/**
	 * 用于前端select,radio,radiobox值 key-value==id-name
	 * 
	 * @param hql
	 * @param obj
	 * @return List<KeyValueResult>
	 */
	public <T> List<KeyValueResult> queryKeyValueResultsByProperties(
			Class<T> entityClass, String hql, Object... obj);

	/**
	 * 用于前端select,radio,radiobox值 key-value==id-name
	 * 
	 * @param id
	 *            排除id
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
			List<Object> parentList, Class<T> entityClass);

	/**
	 * 获取指定类型数据 tree=>id,name,icon,classname,p
	 * @param id ,排除id
	 * @param fieldMap ,Map<field,logic> a = ? and b = ? or c = ?
	 * @param parentList ,参数值
	 * @param entityCustomer
	 * @param entityClass ,查询的对象
	 * @return List<E>
	 */
	public <E extends BaseBean, T extends BaseBean> List<E> finalResultsByCustomerEntity(
			Serializable id, Map<String, String> fieldMap,
			List<Object> parentList, Class<E> entityCustomer,
			Class<T> entityClass);

	/**
	 * 持久化对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void save(T entity);

	/**
	 * 新建与修改
	 * 
	 * @param entity
	 */
	public <T extends BaseBean> void saveOrUpdate(T entity);

	/**
	 * 根据多个id参数删除对象
	 * 
	 * @param id
	 *            多个id，以英文逗号隔开
	 * @return boolean
	 */
	public <T extends BaseBean> boolean deleteByPK(Class<T> entityClass,
			Serializable... id);

	/**
	 * 删除对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void delete(T entity);

	/**
	 * 以HQL的方式，根据单个属性删除对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 */
	public <T extends BaseBean> void deleteByProperties(String propName,
			Object propValue, Class<T> entityClass);

	/**
	 * 以HQL的方式，根据多个属性删除对象实体
	 * 
	 * @param propName
	 *            属性名称
	 * @param propValue
	 *            属性值
	 */
	public <T extends BaseBean> void deleteByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass);

	/**
	 * 根据给定的Detached对象标识符更新对象实体
	 * 
	 * @param entity
	 *            对象实体
	 */
	public <T extends BaseBean> void update(T entity);

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
			Object[] propertyValue, Class<T> entityClass);

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
			Class<T> entityClass);

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
			Object[] propertyValue, Class<T> entityClass);

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
			Class<T> entityClass);

	/**
	 * 先删除再插入去更新对象实体
	 * 
	 * @param entity
	 *            待更新的对象实体
	 * @param oldId
	 *            已存在的对象实体主键
	 */
	public <T extends BaseBean> void update(T entity, Class<T> entityClass,
			Serializable oldId);

	/**
	 * 合并给定的对象实体状态到当前的持久化上下文
	 * 
	 * @param entity
	 *            给定的对象实体
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T merge(T entity);

	/**
	 * 根据ID立即加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T get(Serializable id, Class<T> entityClass);

	/**
	 * 根据ID立即加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T get(String id, Class<T> entityClass);

	/**
	 * 根据ID延迟加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T load(Serializable id, Class<T> entityClass);

	/**
	 * 根据ID延迟加载持久化对象实体
	 * 
	 * @param id
	 *            ID值
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T load(String id, Class<T> entityClass);

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
			Object[] propValue, Class<T> entityClass);

	/**
	 * 根据属性数组和排序条件获取单个对象实体
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param sortedCondition
	 *            排序条件
	 * @return 返回对象实体
	 */
	public <T extends BaseBean> T getByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Class<T> entityClass);

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
			Object propValue, Class<T> entityClass);

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
			Class<T> entityClass);

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
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Integer top, Class<T> entityClass);

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
			Class<T> entityClass);

	/**
	 * 根据属性和要返回的记录数目获取对象实体列表
	 * 
	 * @param propName
	 *            属性数组名称
	 * @param propValue
	 *            属性数组值
	 * @param top
	 *            要返回的记录数目
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Integer top, Class<T> entityClass);

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
			Object[] propValue, Class<T> entityClass);

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
			Class<T> entityClass);

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
			Class<T> entityClass);

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
			Object propValue, Integer top, Class<T> entityClass);

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
			Object propValue, Class<T> entityClass);

	/**
	 * 刷新会话
	 * 
	 * @param entity
	 */
	public <T extends BaseBean> void refresh(T entity);

	/**
	 * 强制刷新会话缓存
	 */
	public <T extends BaseBean> void flush();

	/**
	 * 彻底清除会话
	 */
	public void clear();

	/**
	 * 从会话缓存中删除此对象实体
	 * 
	 * @param entity
	 *            待删除的对象实体
	 */
	public <T extends BaseBean> void evict(T entity);

	/**
	 * 查询出对象实体的所有数目
	 * 
	 * @return 返回对象实体所有数目
	 */
	public <T extends BaseBean> Long countAll(Class<T> entityClass);

	/**
	 * 查询出所有的对象实体列表
	 * 
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQueryAll(Class<T> entityClass);

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
			Class<T> entityClass);

	/**
	 * 根据要返回的记录数目查询出对象实体列表
	 * 
	 * @param top
	 *            要返回的记录数目
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQueryAll(Integer top,
			Class<T> entityClass);

	/**
	 * 根据各种查询条件返回对象实体数目
	 * 
	 * @param parameter
	 *            各种查询条件
	 * @return 返回对象实体数目
	 */
	public <T extends BaseBean> Long doCount(BaseParameter parameter,
			Class<T> entityClass);

	/**
	 * 根据各种查询条件返回对象实体列表
	 * 
	 * @param parameter
	 *            各种查询条件
	 * @return 返回对象实体列表
	 */
	public <T extends BaseBean> List<T> doQuery(BaseParameter parameter,
			Class<T> entityClass);

	/**
	 * 根据各种查询条件返回分页列表
	 * 
	 * @param parameter
	 *            各种查询条件
	 * @return 返回分页列表
	 */
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter parameter, Class<T> entityClass);

	/**
	 * 根据各种查询条件返回分页列表
	 * 
	 * @param parameter
	 *            各种查询条件
	 * @param bool
	 *            默认为true；如果为false，增加属性是否为空等查询条件
	 * @return 返回分页列表
	 */
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter parameter, boolean bool, Class<T> entityClass);

}
