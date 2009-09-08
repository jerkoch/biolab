package com.bc.cisanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.HashMap;
import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;

public class ResultReader {
	private File GOResults;
	private File GOagi;
	private BufferedReader goReader;
	private GOagiReader agiReader;
	private PrintWriter printer;
	private String pattern;
	
	public ResultReader(File GOFile, File AGIFile, PrintWriter writer) {
		printer = writer;
		GOResults = GOFile;
		GOagi = AGIFile;
		pattern = GOResults.getName();
		pattern = pattern.substring(0, pattern.lastIndexOf(".txt.processed.txt"));
		try {
			goReader = new BufferedReader(new FileReader(GOResults));
			agiReader = new GOagiReader(new FileInputStream(GOagi));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GeneDescriptorMap<GO> parseGOResults() {
		HashMap<GO, Set<AGI>> goMap = new HashMap<GO, Set<AGI>>();
		GO nextGO;
		while ((nextGO = getSignificantGO()) != null) {
			Set<AGI> agis = agiReader.getAGIs(nextGO);
			goMap.put(nextGO, agis);
		}
		GeneDescriptorMap<GO> gdMap = new GeneDescriptorMap<GO>(goMap);
		return gdMap;
	}
	
	private GO getSignificantGO() {
		String nextLine = "";
		try {
			while ((nextLine = goReader.readLine()) != null) {
				String nextLineSplit[] = nextLine.split("\t");
				double pval = Double.parseDouble(nextLineSplit[6].trim());
				if (pval < 0.001) {
					String goId = nextLineSplit[0].trim();
					String goDesc = nextLineSplit[1].trim();
					printer.println(pattern
							+ '\t' + goDesc
							+ '\t' + String.valueOf(Math.log10(pval)));
					return GO.createGO(goId, goDesc);
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}