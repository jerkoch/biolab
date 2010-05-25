package org.bradylab.plates.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;

	/**
	 * @return returns the Entity's id
	 */
	public Key getId() {
		return id;
	}
	/**
	 * @param id sets the Entity's id
	 */
	public void setId(Key id) {
		this.id = id;
	}
	
	public boolean isNew() {
		return (id == null);
	}
}
