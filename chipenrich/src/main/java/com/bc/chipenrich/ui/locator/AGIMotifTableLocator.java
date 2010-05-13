package com.bc.chipenrich.ui.locator;

import com.bc.chipenrich.ui.CustomPlant;

public class AGIMotifTableLocator extends AbstractLocator {
	
	public static AGIMotifTableLocator INSTANCE = new AGIMotifTableLocator();
	
	protected String getArabidopsisDefault() {
		return "arabidopsis/AGI_Motif_Table.txt";
	}
	
	protected String getSoybeanDefault() {
		return "soybean/AGI_Motif_Table";
	}

	public static AGIMotifTableLocator getInstance() {
		return INSTANCE;
	}
	
	protected String getField(CustomPlant plant) {
		return plant.getTable();
	}
	
	protected String getType() {
		return "AGI Motif Table";
	}
}
