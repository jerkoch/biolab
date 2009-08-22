package com.bc.cisanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.Iterator;
import com.bc.core.AGI;
import com.bc.core.GO;

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
	
	public void parseGOResults() {
		try {
			String pattern = GOResults.getName();
			pattern = pattern.substring(0, pattern.lastIndexOf(".txt.processed.txt"));
			resultReader = new GOReader(new FileInputStream(GOResults));
			agiReader = new GOagiReader(new FileInputStream(GOagi));
			GO nextGO;
			int goNum = 1;
			while ((nextGO = resultReader.getSignificantGO()) != null) {
				Set<AGI> agis = agiReader.getAGIs(nextGO);
				goNum++;
				printer.println(pattern
						+ '\t'
						+ nextGO.getDescription());
				Iterator<AGI> it = agis.iterator();
				while (it.hasNext()) {
					// Do something with AGIs
					AGI nextAGI = it.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
