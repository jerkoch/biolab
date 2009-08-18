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
	public void getCisCount(BackgroundChip backgroundChip, String motifFileName,
			File inputFile, AGIMotifReader tableReader, String outputDir) {
		try {
			new File(outputDir).mkdir();
			
			InputStream motifFile = getClass().getClassLoader().getResourceAsStream(motifFileName);
			
			String outFileName = inputFile.getName() + ".processed.txt";
//			FileOutputStream outfile = new FileOutputStream(outputDir + outFileName);
//			PrintWriter printer = new PrintWriter(outfile);
			File outfile = new File(outputDir, outFileName);
			PrintStream printer = new PrintStream(outfile);
			
			String outFileNameAGI = inputFile.getName() + ".processed.agi_list.txt";
//			FileOutputStream outfileAGI = new FileOutputStream(outputDir + outFileNameAGI);
//			PrintWriter printerAGI = new PrintWriter(outfileAGI);
			File outfileAGI = new File(outputDir, outFileNameAGI);
			PrintStream printerAGI = new PrintStream(outfileAGI);
			
			AGIQueryListParser parser = new AGIQueryListParser();
			parser.parse(new FileInputStream(inputFile));
			Set queryList = parser.getAGIs();
			MotifReader motifReader = new MotifReader(motifFile);
			String nextMotif;
			//For each motif in from queryfile
			while ((nextMotif = motifReader.getMotif()) != null) {
				boolean found = false;
				int foundBackground = 0;
				int foundInFile = 0;
				printerAGI.print(nextMotif);
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
			            	printerAGI.print("\t" + nextAGI.getId());
			            }
					}
				}
				printerAGI.println("");
			    printer.println(nextMotif
			            + "\t"
			            + foundInFile
			            + "\t"
			            + queryList.size()
			            + "\t"
			            + foundBackground
			            + "\t"
			            + backgroundChip.getNumAGIs()
			            + "\t"
			            + DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
			                  queryList.size(), foundInFile));
			}	//end while
			printer.close();
			printerAGI.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
