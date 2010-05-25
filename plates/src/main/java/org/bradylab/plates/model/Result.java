package org.bradylab.plates.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Result extends AbstractEntity {

	private Date creationTime;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DataPoint> dataPoints = new HashSet<DataPoint>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Plate plate;

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Plate getPlate() {
		return plate;
	}

	public void setPlate(Plate plate) {
		this.plate = plate;
	}

	public Set<DataPoint> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(Set<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}
}
