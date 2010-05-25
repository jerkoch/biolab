package org.bradylab.plates.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class WebPlate {

	private String id;
	
	@DateTimeFormat(style="SS")	
	private Date creationTime = new Date();
	
	private String name;
	private int width;
	private int height;
	private int subDivide = 1;

	private WebResult result;
	private WebLabel label;

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

	public WebResult getResult() {
		return result;
	}

	public void setResult(WebResult result) {
		this.result = result;
	}

	public WebLabel getLabel() {
		return label;
	}

	public void setLabel(WebLabel label) {
		this.label = label;
	}
}
