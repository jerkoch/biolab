/*
**
*/
package com.bc.promomer.service;

import java.io.File;
import javax.swing.JLabel;

import com.bc.core.BackgroundChip;
import com.bc.util.ResourceUtil;
import com.bc.chipenrich.service.ChipEnrichService;

public class PromomerRunner extends Thread {
	
	private ChipEnrichService ces;
	private JLabel status;
	private File[] queryFiles;
	private String backgroundChipFilename;
	private File upstreamFile;
	
	public PromomerRunner(JLabel status, ChipEnrichService ces, File upstreamFile,
			File[] queryFiles, String backgroundChipFilename) {
		this.ces = ces;
		this.status = status;
		this.upstreamFile = upstreamFile;
		this.queryFiles = queryFiles;
		this.backgroundChipFilename = backgroundChipFilename;
	}
	
	public void run() {
		status.setText(getRunnerName() + ": Processing background chip...");
		BackgroundChip backgroundChip = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream(
	            backgroundChipFilename));
		PromomerService ps = new PromomerServiceImpl();
		//get motifs from file
		String motif = "";
		ps.getCisCount(backgroundChip, upstreamFile, motif, queryFiles);
		
		//output to motifs/ath1chip
		//output to motifs/singletons
	}
	
	protected String getRunnerName() {
		return "Promomer Service";
	}
}