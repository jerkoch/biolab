package org.bradylab.plates.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {

	@Basic
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
