package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class SingletonChipLocator extends AbstractLocator {
	private static SingletonChipLocator INSTANCE = new SingletonChipLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/SINGLETONS.txt";
	}
	
	protected String getSoybeanDefault() {
		return "soybean/SINGLETONS.txt";
	}
	
	public static SingletonChipLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getSingleton();
	}
	
	protected String getType() {
		return "Singleton Chip";
	}
}