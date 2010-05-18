package com.bc.promomer.service.runner;

import javax.swing.JLabel;
import java.io.File;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.locator.WholeChipLocator;
import com.bc.file.AGIMotifReader;

public class ATH1PromomerRunner extends PromomerRunner {
	public ATH1PromomerRunner(JLabel status, ChipEnrichService ces, 
			AGIMotifReader tableReader, File[] queryFiles, String baseOutputDir) {
		super(status, tableReader, queryFiles, "wholechip", baseOutputDir, 
				ces.processBackgroundChip(WholeChipLocator.getInstance().getInputStream()));
	}
	
	protected String getRunnerName() {
		return "ATH1 Promomer";
	}

}
