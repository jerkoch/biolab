package com.bc.promomer.service.runner;

import javax.swing.JLabel;
import java.io.File;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.chooser.PlantChooser;
import com.bc.file.AGIMotifReader;

public class ATH1PromomerRunner extends PromomerRunner {
	public ATH1PromomerRunner(JLabel status, ChipEnrichService ces, 
			AGIMotifReader tableReader, File[] queryFiles, String baseOutputDir) {
		super(status, ces, tableReader, queryFiles, "ath1chip", baseOutputDir, 
				PlantChooser.getInstance().getPlant() + "/ATH1Chip.txt");
	}
	
	protected String getRunnerName() {
		return "ATH1 Promomer";
	}

}
