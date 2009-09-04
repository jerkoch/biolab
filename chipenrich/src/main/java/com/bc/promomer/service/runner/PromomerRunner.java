/*
**
*/
package com.bc.promomer.service.runner;

import java.io.File;
import javax.swing.JLabel;

import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.chipenrich.domain.ResultsHandler;
import com.bc.core.BackgroundChip;
import com.bc.promomer.service.PromomerTable;
import com.bc.promomer.service.PromomerTableImpl;
import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.file.AGIMotifReader;

public abstract class PromomerRunner extends Thread {
	
	private ChipEnrichService ces;
	private JLabel status;
	private File[] queryFiles;
	private String backgroundChipFilename;
	private AGIMotifReader tableReader;
	private String root;
	private String baseOutputDir;
	
	public PromomerRunner(JLabel status, ChipEnrichService ces, AGIMotifReader tableReader, 
			File[] queryFiles, String root, String baseOutputDir, String backgroundChipFilename) {
		this.ces = ces;
		this.status = status;
		this.tableReader = tableReader;
		this.queryFiles = queryFiles;
		this.backgroundChipFilename = backgroundChipFilename;
		this.root = root;
		this.baseOutputDir = baseOutputDir;
	}
	
	public void run() {
		status.setText(getRunnerName() + ": Processing background chip...");
		BackgroundChip backgroundChip = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream(
	            backgroundChipFilename));
		PromomerTable pt = new PromomerTableImpl(backgroundChip, tableReader);
		
		String outputDir = baseOutputDir + "/" + root + "/motifs";
		
		status.setText(getRunnerName() + ": Processing Motifs");
		EnrichmentSummary summary = new EnrichmentSummary();
		for (int i = 0; i < queryFiles.length; i++) {
			status.setText(getRunnerName() + ": Processing Motifs: " + queryFiles[i].getName());
			pt.getCisCount(queryFiles[i], outputDir, summary);
		}
		File summaryOut = new File(outputDir + "/summary.txt");
		ResultsHandler.outputSummary(summaryOut, summary);
	}
	
	protected abstract String getRunnerName();
}