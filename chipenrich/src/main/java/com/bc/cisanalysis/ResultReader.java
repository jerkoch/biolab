package com.bc.cisanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.HashMap;
import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;

public class ResultReader {
	private File GOResults;
	private File GOagi;
	private GOReader resultReader;
	private GOagiReader agiReader;
	private PrintStream printer;
	
	public ResultReader(File GOFile, File AGIFile, PrintStream writer) {
		printer = writer;
		GOResults = GOFile;
		GOagi = AGIFile;
	}
	
	public GeneDescriptorMap<GO> parseGOResults() {
		try {
			String pattern = GOResults.getName();
			pattern = pattern.substring(0, pattern.lastIndexOf(".txt.processed.txt"));
			resultReader = new GOReader(new FileInputStream(GOResults));
			agiReader = new GOagiReader(new FileInputStream(GOagi));
			HashMap<GO, Set<AGI>> goMap = new HashMap<GO, Set<AGI>>();
			GO nextGO;
			int goNum = 1;
			while ((nextGO = resultReader.getSignificantGO()) != null) {
				Set<AGI> agis = agiReader.getAGIs(nextGO);
				goNum++;
				printer.println(pattern
						+ '\t'
						+ nextGO.getDescription());
				goMap.put(nextGO, agis);
			}
			GeneDescriptorMap<GO> gdMap = new GeneDescriptorMap<GO>(goMap);
			return gdMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
