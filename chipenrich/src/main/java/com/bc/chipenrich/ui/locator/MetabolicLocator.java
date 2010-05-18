package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class MetabolicLocator extends AbstractLocator {
	private static MetabolicLocator INSTANCE = new MetabolicLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/metabolicpathways2008.txt";
	}
	
	protected String getSoybeanDefault() {
		return "soybean/Soybean_Pathway.txt";
	}
	
	public static MetabolicLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getMetabolic();
	}
	
	protected String getType() {
		return "Metabolic Pathways";
	}
}