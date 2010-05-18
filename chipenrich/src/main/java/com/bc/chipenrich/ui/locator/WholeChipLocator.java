package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class WholeChipLocator extends AbstractLocator {
	private static WholeChipLocator INSTANCE = new WholeChipLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/ATH1Chip.txt";
	}
	
	protected String getSoybeanDefault() {
		return "soybean/Soybean_TranscriptOnArray_WholeChip.txt";
	}
	
	public static WholeChipLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getWholechip();
	}
	
	protected String getType() {
		return "Whole Chip";
	}
}
