package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

import com.bc.chipenrich.ui.MotifFileLocator;
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
			String outFileNameAGI = inputFile.getName() + ".processed.agi_list.txt";
			File outfileAGI = new File(outputDir, outFileNameAGI);
			
			PrintWriter printer = null;
			PrintWriter printerAGI = null;
			try {
				printer = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
				printerAGI = new PrintWriter(new BufferedWriter(new FileWriter(outfileAGI)));
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
						queryList, printer, printerAGI);
			}	//end while
			printer.close();
			printerAGI.close();
	}
	
	public double parseLine(String patternName, String nextMotif, String nextElement, 
			Set<AGI> queryList, PrintWriter printer, 
			PrintWriter printerAGI) {
		int foundBackground = 0;
		int foundInFile = 0;
		boolean AGIprint = false;
		String nextAGIs = "";
		int numAGIs = 0;
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
		            	numAGIs++;
		            }
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
		CIS motif = new CIS(nextMotif, nextElement);
		ProbabilityResult pr = ProbabilityResult.createProbabilityResult(motif, foundInFile, queryList.size(), foundBackground, backgroundChip.getNumAGIs());
		if (pr.isSignificant()) {
			if (summary != null) {
				summary.add(patternName, pr);
			}
			if (printer != null) {
				printer.println(nextElement
		            + "\t" + foundInFile
		            + "\t" + queryList.size()
		            + "\t" + foundBackground
		            + "\t" + backgroundChip.getNumAGIs()
		            + "\t" + pval);
			}
		}
		try {
			printer.flush();
			printerAGI.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pval;
	}
}
