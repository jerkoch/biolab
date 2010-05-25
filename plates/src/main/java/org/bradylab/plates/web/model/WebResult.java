package org.bradylab.plates.web.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WebResult {

	private String id;
	private Date creationTime;
	private Set<WebDataPoint> dataPoints = new HashSet<WebDataPoint>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Set<WebDataPoint> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(Set<WebDataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}
}
