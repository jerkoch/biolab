package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class BindingSiteLocator extends AbstractLocator {
	private static BindingSiteLocator INSTANCE = new BindingSiteLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/matching_binding_site_no_spaces_2.txt";
	}
	
	protected String getSoybeanDefault() {
		return "arabidopsis/matching_binding_site_no_spaces_2.txt";
	}
	
	public static BindingSiteLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getBindingSite();
	}
	
	protected String getType() {
		return "Binding Site";
	}
}