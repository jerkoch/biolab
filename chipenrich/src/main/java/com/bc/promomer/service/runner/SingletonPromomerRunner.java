package com.bc.promomer.service.runner;

import javax.swing.JLabel;
import java.io.File;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.chooser.PlantChooser;
import com.bc.file.AGIMotifReader;

public class SingletonPromomerRunner extends PromomerRunner {
	public SingletonPromomerRunner(JLabel status, ChipEnrichService ces, 
			AGIMotifReader tableReader, File[] queryFiles, String baseOutputDir) {
		super(status, ces, tableReader, queryFiles, "singletons", baseOutputDir, 
				PlantChooser.getInstance().getPlant() + "/SINGLETONS.txt");
	}
	
	protected String getRunnerName() {
		return "Singleton Promomer";
	}

}
