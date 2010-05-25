package org.bradylab.plates.persistence.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bradylab.plates.model.AbstractEntity;
import org.bradylab.plates.persistence.BaseDao;
import org.springframework.orm.ObjectRetrievalFailureException;

public abstract class BaseDaoImpl<T extends AbstractEntity, KeyType> implements BaseDao<T, KeyType> {

    @PersistenceContext
    private EntityManager entityManager;
    
	protected Class<T> domainClass = getDomainClass();

	/**
	 * Returns the EntityManager reference
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the domain class extending this class
	 * 
	 * @return domainClass
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.rr.persistance.BaseDao#load(java.lang.Object)
	 */
	public T load(KeyType id) throws ObjectRetrievalFailureException {
		T entity = entityManager.find(domainClass, id);
		if (entity == null) {
			throw new ObjectRetrievalFailureException(domainClass, id);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.rr.persistance.BaseDao#loadAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadAll(Object orderByField) {
		Query query = entityManager.createQuery("select obj from " + domainClass.getName() + " obj order by obj.id " + orderByField);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.rr.persistance.BaseDao#save(com.ibm.rr.domain.AbstractEntity)
	 * TODO investigate entitiy.isNew();
	 */
	public T save(T entity) {
		if (entity.isNew()) {
			// Create entity
			entityManager.persist(entity);
		} else {
			// Update entity
			entityManager.merge(entity);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.rr.persistance.BaseDao#delete(com.ibm.rr.domain.AbstractEntity)
	 */
	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.rr.persistance.BaseDao#deleteById(java.lang.Object)
	 */
	public void deleteById(KeyType id) {
		T loadedEntity = load(id);
		entityManager.remove(loadedEntity);
	}

}
