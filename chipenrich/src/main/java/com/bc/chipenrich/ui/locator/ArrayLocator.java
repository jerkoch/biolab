package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class ArrayLocator extends AbstractLocator {
	public static ArrayLocator INSTANCE = new ArrayLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/ArrayAnnotationSummary.txt";
	}
	
	protected String getSoybeanDefault() {
		return null;
	}
	
	public static ArrayLocator getInstance() {
		return INSTANCE;
	}

	protected String getField(CustomPlant plant) {
		return plant.getArray();
	}
	
	protected String getType() {
		return "Array";
	}
}
