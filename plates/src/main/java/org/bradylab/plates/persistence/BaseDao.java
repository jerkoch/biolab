package org.bradylab.plates.persistence;

import java.util.List;

import org.bradylab.plates.model.AbstractEntity;

/**
 * 
 * Base DAO objects that all DAOs will implement
 * 
 */
public interface BaseDao<T extends AbstractEntity, KeyType> {

    /**
     * Load an existing entity by its id
     * @param id
     * @return Entity that was loaded
     */
    public T load(KeyType id);
    
    /**
     * Load all entities
     * @return List of entities
     */
    public List<T> loadAll(Object orderByField);

    /**
     * Save an new or existing entity
     * @param object
     * @return Entity that was created/updated
     */
    public T save(T object);

    /**
     * Delete an existing entity
     * @param object
     */
    public void delete(T object);
    
    /**
     * Delete an existing entity by id
     * @param id
     */
    public void deleteById(KeyType id);
	
}
