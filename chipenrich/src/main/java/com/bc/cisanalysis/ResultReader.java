package com.bc.cisanalysis;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;

public class ResultReader {
	private File GOResults;
	private BufferedReader goReader;
	private PrintWriter printer;
	private String pattern;
	
	public ResultReader(File GOFile, PrintWriter writer) {
		printer = writer;
		GOResults = GOFile;
		pattern = GOResults.getName();
		pattern = pattern.substring(0, pattern.lastIndexOf(".txt.go.processed.txt"));
		try {
			goReader = new BufferedReader(new FileReader(GOResults));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GeneDescriptorMap<GO> parseGOResults() {
		HashMap<GO, Set<AGI>> goMap = new HashMap<GO, Set<AGI>>();
		String nextLine = "";
		GO nextGO;
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
					nextGO = GO.createGO(goId, goDesc);
					Set<AGI> agis = new TreeSet<AGI>();
					for (int i = 7; i < nextLineSplit.length; i++) {
						agis.add(AGI.createAGI(nextLineSplit[i]));
					}
					goMap.put(nextGO, agis);
				}
			}
			GeneDescriptorMap<GO> gdMap = new GeneDescriptorMap<GO>(goMap);
			return gdMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}