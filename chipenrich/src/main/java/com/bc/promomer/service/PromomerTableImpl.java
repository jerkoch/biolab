package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Set;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;
import com.bc.file.MotifReader;
import com.bc.util.DistributionCalculator;

public class PromomerTableImpl implements PromomerTable{
	private BackgroundChip backgroundChip;
	private AGIMotifReader tableReader;
	
	public PromomerTableImpl(BackgroundChip backgroundChip, AGIMotifReader table) {
		this.backgroundChip = backgroundChip;
		this.tableReader = table;
	}
	public void getCisCount(String motifFileName,
			File inputFile, String outputDir) {
		try {
			new File(outputDir).mkdir();
			
			InputStream motifFile = getClass().getClassLoader().getResourceAsStream(motifFileName);
			
			String outFileName = inputFile.getName() + ".processed.txt";
			File outfile = new File(outputDir, outFileName);
			PrintStream printer = new PrintStream(outfile);
			
			String outFileNameAGI = inputFile.getName() + ".processed.agi_list.txt";
			File outfileAGI = new File(outputDir, outFileNameAGI);
			PrintStream printerAGI = new PrintStream(outfileAGI);
			
			AGIQueryListParser parser = new AGIQueryListParser();
			parser.parse(new FileInputStream(inputFile));
			Set<AGI> queryList = parser.getAGIs();
			MotifReader motifReader = new MotifReader(motifFile);
			//For each motif in from queryfile
			while (motifReader.nextLine()) {
				parseLine(motifReader.getMotif(), motifReader.getElement(),
						queryList, printer, printerAGI);
			}	//end while
			printer.close();
			printerAGI.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double parseLine(String nextMotif, String nextElement, 
			Set<AGI> queryList, PrintStream printer, 
			PrintStream printerAGI) {
		boolean found = false;
		int foundBackground = 0;
		int foundInFile = 0;
		boolean AGIprint = false;
		String nextAGIs = "";
		int numAGIs = 0;
		//For each AGI:
		for (int i = 0; i < tableReader.numAGIs(); i++) {
			found = false;
			AGI nextAGI = tableReader.getAGIat(i);
			//If the AGI is in the background chip, check if motif is found in AGI
			if (backgroundChip.getAGIs().contains(nextAGI)) {
				//FIXME:  do something if getCount returns < 0 (not in AGI-Motif table)
				if (tableReader.getCount(nextAGI.getId(), nextMotif) > 0) {
					foundBackground++;
					found = true;
				}
	            if (found && queryList.contains(nextAGI)) {
	            	foundInFile++;
	            	if (AGIprint == false) {
	            		AGIprint = true;
	            	}
	            	nextAGIs += (("\t") + nextAGI.getId());
	            	numAGIs++;
	            }
			}
		}
		if (AGIprint && (printerAGI != null)) {
			printerAGI.println(nextElement
					+ "\t"
					+ numAGIs
					+ nextAGIs);
		}
		double pval = DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
                queryList.size(), foundInFile);
		if (printer != null) {
			printer.println(nextElement
	            + "\t"
	            + foundInFile
	            + "\t"
	            + queryList.size()
	            + "\t"
	            + foundBackground
	            + "\t"
	            + backgroundChip.getNumAGIs()
	            + "\t"
	            + pval);
		}
		return pval;
	}
}
