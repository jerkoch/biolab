package com.bc.promomer.service.runner;

import javax.swing.JLabel;
import java.io.File;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.locator.SingletonChipLocator;
import com.bc.file.AGIMotifReader;

public class SingletonPromomerRunner extends PromomerRunner {
	public SingletonPromomerRunner(JLabel status, ChipEnrichService ces, 
			AGIMotifReader tableReader, File[] queryFiles, String baseOutputDir) {
		super(status, tableReader, queryFiles, "singletons", baseOutputDir, 
				ces.processBackgroundChip(SingletonChipLocator.getInstance().getInputStream()));
	}
	
	protected String getRunnerName() {
		return "Singleton Promomer";
	}

}
