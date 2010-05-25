package org.bradylab.plates.model;

import javax.persistence.Entity;

@Entity
public class DataPoint extends AbstractEntity {

	private String x;
	private String y;
	private String z;

	public DataPoint(String x, String y) {
		this.x = x;
		this.y = y;
	}

	public DataPoint(String x, String y, String z) {
		this(x, y);
		this.z = z;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	public String getZ() {
		return z;
	}
	
	public void setZ(String z) {
		this.z = z;
	}

	public int hashCode() {
		return (x != null ? x.hashCode() * 37 : 0) + (y != null ? y.hashCode() * 43 : 0) + (z != null ? z.hashCode() * 47 : 0);
	}

	public boolean equals(Object obj) {
		if (obj instanceof DataPoint) {
			DataPoint dp = (DataPoint) obj;
			return x != null && x.equals(dp.getX()) && y != null && y.equals(dp.getY()) && z != null && z.equals(dp.getZ());
		}
		return super.equals(obj);
	}
}
