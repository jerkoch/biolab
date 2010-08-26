package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class TFFLocator extends AbstractLocator {
	private static TFFLocator INSTANCE = new TFFLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/tff_summary_082410.txt";
	}
	
	protected String getSoybeanDefault() {
		return "soybean/Soybean_TF.txt";
	}
	
	public static TFFLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getTFF();
	}
	
	protected String getType() {
		return "Transcription Factor Family";
	}
}