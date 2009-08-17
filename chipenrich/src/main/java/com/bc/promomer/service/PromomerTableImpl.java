package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;
import com.bc.file.MotifReader;
import com.bc.util.DistributionCalculator;

public class PromomerTableImpl implements PromomerTable{
	public void getCisCount(BackgroundChip backgroundChip, File AGIMotifTable, File motifFile,
			File[] inputFiles) {
		try {
			Set[] queryList = new Set[inputFiles.length];
			for (int i = 0; i < inputFiles.length; i++) {
				System.out.println(inputFiles[i].getName());
				AGIQueryListParser parser = new AGIQueryListParser();
				parser.parse(new FileInputStream(inputFiles[i]));
				queryList[i] = parser.getAGIs();
			}
			AGIMotifReader tableReader = new AGIMotifReader(new FileInputStream(AGIMotifTable));
			MotifReader motifReader = new MotifReader(new FileInputStream(motifFile));
			String nextMotif;
			System.out.println(tableReader.getCount(AGI.createAGI("AT1G51780"), "YACGTGGC"));
			while ((nextMotif = motifReader.getMotif()) != null) {
				boolean found = false;
				int foundBackground = 0;
				int[] foundInFiles = new int[inputFiles.length];
				for (int i = 0; i < tableReader.numAGIs(); i++) {
					found = false;
					AGI nextAGI = tableReader.getAGIat(i);
					if (backgroundChip.getAGIs().contains(nextAGI)) {
						if (tableReader.getCount(nextAGI, nextMotif) > 0) {
							foundBackground++;
							found = true;
						}
			            for (int j = 0; j < inputFiles.length; j++) {
			            	if (found && queryList[j].contains(nextAGI)) {
//			            		System.out.println(nextAGI);
			            		foundInFiles[j]++;
			            	}
			            }
					}
				}
			    for (int i = 0; i < inputFiles.length; i++) {
			    	System.out.println(inputFiles[i].getName()
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
		} catch (Exception e) {
			   
		}
	}
}
