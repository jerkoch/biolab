package com.bc.ui.locator;

public class AffyATH1Locator extends AbstractLocator {
	private static AffyATH1Locator INSTANCE = new AffyATH1Locator();
	
	protected String getDefault() {
		return "affy_ATH1_array_elements-2008-5-29.txt";
	}
	
	protected String getType() {
		return "Affy / ATH1";
	}
	
	public static AffyATH1Locator getInstance() {
		return INSTANCE;
	}
}
