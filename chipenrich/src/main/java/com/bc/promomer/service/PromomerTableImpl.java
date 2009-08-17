package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Set;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;
import com.bc.file.MotifReader;
import com.bc.util.DistributionCalculator;

public class PromomerTableImpl implements PromomerTable{
	public void getCisCount(BackgroundChip backgroundChip, File motifFile,
			File[] inputFiles, AGIMotifReader tableReader, String outputDir) {
		try {
			FileOutputStream outfile = new FileOutputStream(outputDir + "motifs.txt");
			PrintWriter printer = new PrintWriter(outfile);
			Set[] queryList = new Set[inputFiles.length];
			for (int i = 0; i < inputFiles.length; i++) {
				AGIQueryListParser parser = new AGIQueryListParser();
				parser.parse(new FileInputStream(inputFiles[i]));
				queryList[i] = parser.getAGIs();
			}
			MotifReader motifReader = new MotifReader(new FileInputStream(motifFile));
			String nextMotif;
			//For each motif in from queryfile
			while ((nextMotif = motifReader.getMotif()) != null) {
				boolean found = false;
				int foundBackground = 0;
				int[] foundInFiles = new int[inputFiles.length];
				//For each AGI:
				for (int i = 0; i < tableReader.numAGIs(); i++) {
					found = false;
					AGI nextAGI = tableReader.getAGIat(i);
					//If the AGI is in the background chip, check if motif is found in AGI
					if (backgroundChip.getAGIs().contains(nextAGI)) {
						//FIXME:  send to Promomer service if getCount returns < 0 (not in AGI-Motif table)
						if (tableReader.getCount(nextAGI.getId(), nextMotif) > 0) {
							foundBackground++;
							found = true;
						}
			            for (int j = 0; j < inputFiles.length; j++) {
			            	if (found && queryList[j].contains(nextAGI)) {
			            		foundInFiles[j]++;
			            	}
			            }
					}
				}
			    for (int i = 0; i < inputFiles.length; i++) {
			    	printer.println(inputFiles[i].getName()
			              + "\t"
			              + nextMotif
			              + "\t"
			              + foundInFiles[i]
			              + "\t"
			              + queryList[i].size()
			              + "\t"
			              + foundBackground
			              + "\t"
			              + backgroundChip.getNumAGIs()
			              + "\t"
			              + DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
			                    queryList[i].size(), foundInFiles[i]));
			    }
			}
			printer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
