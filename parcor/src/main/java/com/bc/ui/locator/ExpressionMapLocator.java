package com.bc.ui.locator;

public class ExpressionMapLocator extends AbstractLocator {
	private static ExpressionMapLocator INSTANCE = new ExpressionMapLocator();
	
	protected String getDefault() {
//		return "fivemarkerlines.txt";
		return "fivemarkerlines2.txt";
	}
	
	protected String getType() {
		return "Expression Map";
	}
	
	public static ExpressionMapLocator getInstance() {
		return INSTANCE;
	}
}
