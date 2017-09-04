package cn.core.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.core.dao.base.IBaseDao;
import cn.core.domain.BaseBean;
import cn.core.domain.support.BaseParameter;
import cn.core.domain.support.KeyValueResult;
import cn.core.domain.support.QueryResult;
import cn.core.service.IBaseSimpleService;

@Transactional
public class BaseServiceImpl implements IBaseSimpleService {
	protected IBaseDao dao;

	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T get(Class<T> entityClass, Serializable id) {
		return this.dao.get(id, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T get(Class<T> entityClass, String id) {
		return this.dao.get(id, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T getByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		return this.dao.getByProperties(propName, propValue, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T getByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		return this.dao.getByProperties(propName, propValue, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T getByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return this.dao.getByProperties(propName, propValue, sortedCondition,
				entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T getByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return this.dao.getByProperties(propName, propValue, sortedCondition,
				entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T load(Serializable id, Class<T> entityClass) {
		return this.dao.load(id, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> T load(String id, Class<T> entityClass) {
		return this.dao.load(id, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, sortedCondition,
				entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition,
			Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, sortedCondition,
				entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Map<String, String> sortedCondition,
			Integer top, Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, sortedCondition,
				top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String[] propName,
			Object[] propValue, Integer top, Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Map<String, String> sortedCondition, Integer top,
			Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, sortedCondition,
				top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> queryByProperties(String propName,
			Object propValue, Integer top, Class<T> entityClass) {
		return this.dao.queryByProperties(propName, propValue, top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> doQueryAll(Class<T> entityClass) {
		return this.dao.doQueryAll(entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> doQueryAll(
			Map<String, String> sortedCondition, Integer top,
			Class<T> entityClass) {
		return this.dao.doQueryAll(sortedCondition, top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> doQueryAll(Integer top,
			Class<T> entityClass) {
		return this.dao.doQueryAll(top, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<T> doQuery(BaseParameter parameter,
			Class<T> entityClass) {
		return this.dao.doQuery(parameter, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter parameter, Class<T> entityClass) {
		return this.dao.doPaginationQuery(parameter, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> QueryResult<T> doPaginationQuery(
			BaseParameter parameter, boolean bool, Class<T> entityClass) {
		return this.dao.doPaginationQuery(parameter, bool, entityClass);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<KeyValueResult> queryKeyValueResultsByProperties(
			Class<T> entityClass, String hql, Object... obj) {
		return this.dao.queryKeyValueResultsByProperties(KeyValueResult.class,
				hql, obj);
	}

	@Cacheable(value = "contentCache")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> List<KeyValueResult> finalKeyValueResults(
			Long id, Map<String, String> fieldMap, List<Object> valueList,
			Class<T> entityClass) {
		return this.dao.finalKeyValueResults(id, fieldMap, valueList,
				entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> T merge(T entity) {
		return this.dao.merge(entity);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void save(T entity) {
		this.dao.save(entity);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void saveOrUpdate(T entity) {
		this.dao.saveOrUpdate(entity);
	}

	@CacheEvict(value = "contentCache")
	public <T extends BaseBean> boolean deleteByPK(Class<T> entityClass,
			Serializable... id) {
		return this.dao.deleteByPK(entityClass, id);
	}

	@CacheEvict(value = "contentCache")
	public <T extends BaseBean> void delete(T entity) {
		this.dao.delete(entity);
	}

	@CacheEvict(value = "contentCache")
	public <T extends BaseBean> void deleteByProperties(String[] propName,
			Object[] propValue, Class<T> entityClass) {
		this.dao.deleteByProperties(propName, propValue, entityClass);
	}

	@CacheEvict(value = "contentCache")
	public <T extends BaseBean> void deleteByProperties(String propName,
			Object propValue, Class<T> entityClass) {
		this.dao.deleteByProperties(propName, propValue, entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void update(T entity) {
		this.dao.update(entity);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void updateByProperties(String[] conditionName,
			Object[] conditionValue, String[] propertyName,
			Object[] propertyValue, Class<T> entityClass) {
		this.dao.updateByProperties(conditionName, conditionValue,
				propertyName, propertyValue, entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void updateByProperties(String conditionName,
			Object conditionValue, String[] propertyName,
			Object[] propertyValue, Class<T> entityClass) {
		this.dao.updateByProperties(conditionName, conditionValue,
				propertyName, propertyValue, entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void updateByProperties(String[] conditionName,
			Object[] conditionValue, String propertyName, Object propertyValue,
			Class<T> entityClass) {
		this.dao.updateByProperties(conditionName, conditionValue,
				propertyName, propertyValue, entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void updateByProperties(String conditionName,
			Object conditionValue, String propertyName, Object propertyValue,
			Class<T> entityClass) {
		this.dao.updateByProperties(conditionName, conditionValue,
				propertyName, propertyValue, entityClass);
	}

	@CachePut(value = "contentCache")
	public <T extends BaseBean> void update(T entity, Class<T> entityClass,
			Serializable oldId) {
		this.dao.update(entity, entityClass, oldId);
	}

	@Override
	public <T extends BaseBean> void refresh(T entity) {
		this.dao.refresh(entity);
	}

	@Override
	public <T extends BaseBean> void flush() {
		this.dao.flush();

	}

	public <T extends BaseBean> void clear() {
		this.dao.clear();
	}

	public <T extends BaseBean> void evict(T entity) {
		this.dao.evict(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> Long countAll(Class<T> entityClass) {
		return this.dao.countAll(entityClass);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public <T extends BaseBean> Long doCount(BaseParameter parameter,
			Class<T> entityClass) {
		return this.dao.doCount(parameter, entityClass);
	}
}
