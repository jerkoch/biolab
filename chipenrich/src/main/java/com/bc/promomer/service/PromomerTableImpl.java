package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

import com.bc.chipenrich.ui.locator.MotifFileLocator;
import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.chipenrich.domain.ProbabilityResult;
import com.bc.core.CIS;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;
import com.bc.file.MotifReader;
import com.bc.util.DistributionCalculator;

/*
 * Implementation of the abstract class PromomerTable.
 * Calculates the enrichment of CIS elements in the querylist.
 */
public class PromomerTableImpl implements PromomerTable{
	private BackgroundChip backgroundChip;
	private AGIMotifReader tableReader;
	private EnrichmentSummary summary;
	
	public PromomerTableImpl(BackgroundChip backgroundChip, AGIMotifReader table) {
		this.backgroundChip = backgroundChip;
		this.tableReader = table;
	}
	public void getCisCount(File inputFile, String outputDir, 
			EnrichmentSummary summary) {
			new File(outputDir).mkdir();
			
			this.summary = summary;
					
			String outFileName = inputFile.getName() + ".processed.txt";
			File outfile = new File(outputDir, outFileName);
			
			PrintWriter printer = null;
			try {
				printer = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			AGIQueryListParser parser = new AGIQueryListParser();
			try {
				parser.parse(new FileInputStream(inputFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Set<AGI> queryList = parser.getAGIs();
			MotifReader motifReader = new MotifReader(MotifFileLocator.getInstance().getInputStream());
			//For each motif, calculate the enrichment
			while (motifReader.nextLine()) {
				parseLine(inputFile.getName(), motifReader.getMotif(), motifReader.getElement(),
						queryList, printer);
			}	//end while
			printer.close();
	}
	
	public double parseLine(String patternName, String nextMotif, String nextElement, 
			Set<AGI> queryList, PrintWriter printer) {
		int foundBackground = 0;
		int foundInFile = 0;
		boolean AGIprint = false;
		String nextAGIs = "";
		//For each AGI:
		for (int i = 0; i < tableReader.numAGIs(); i++) {
			AGI nextAGI = tableReader.getAGIat(i);
			//If the AGI is in the background chip, check if motif is found in AGI
			if (backgroundChip.getAGIs().contains(nextAGI)) {
				//FIXME:  do something if getCount returns < 0 (not in AGI-Motif table)
				if (tableReader.getCount(nextAGI.getId(), nextMotif) > 0) {
					foundBackground++;
		            if (queryList.contains(nextAGI)) {
		            	foundInFile++;
		            	AGIprint = true;
		            	nextAGIs += (("\t") + nextAGI.getId());
		            }
				}
			}
		}
		double pval = DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
                queryList.size(), foundInFile);
		CIS motif = new CIS(nextMotif, nextElement);
		ProbabilityResult pr = ProbabilityResult.createProbabilityResult(motif, foundInFile, queryList.size(), foundBackground, backgroundChip.getNumAGIs());
		if (pr.isSignificant()) {
			if (summary != null) {
				summary.add(patternName, pr);
			}
			if (printer != null) {
				printer.print(nextElement);
		        printer.print("\t");
		        printer.print(foundInFile);
		        printer.print("\t");
		        printer.print(queryList.size());
		        printer.print("\t");
		        printer.print(foundBackground);
		        printer.print("\t");
		        printer.print(backgroundChip.getNumAGIs());
		        printer.print("\t");
		        printer.print(pval);
		        if (AGIprint) {
		        	printer.print(nextAGIs);
		        }
		        printer.println();
			}
		}
		try {
			printer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pval;
	}
}
