package com.bc.ui.locator;

public class PathwayLocator extends AbstractLocator {
	private static PathwayLocator INSTANCE = new PathwayLocator();
	
	protected String getDefault() {
//		return "GeneEachPathwayAll.txt";
		return "aracyc_unordered_20100621.txt";
	}
	
	protected String getType() {
		return "Pathway";
	}
	
	public static PathwayLocator getInstance() {
		return INSTANCE;
	}
}
