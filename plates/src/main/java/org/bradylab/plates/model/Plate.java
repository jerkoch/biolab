package org.bradylab.plates.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.google.appengine.api.datastore.Key;

@Entity
public class Plate extends AbstractEntity {

	private Date creationTime = new Date();
	
	private String name;
	private int width;
	private int height;
	private int subDivide = 1;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Result> results = new ArrayList<Result>();
	
	@Basic
	private List<Key> labels = new ArrayList<Key>();
		
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSubDivide() {
		return subDivide;
	}

	public void setSubDivide(int subDivide) {
		this.subDivide = subDivide;
	}
	
	public List<Result> getResults() {
		return results;
	}
	
	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	public List<Key> getLabels() {
		return labels;
	}
	
	public void setLabels(List<Key> labels) {
		this.labels = labels;
	}
}
