package com.bc.cisanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.HashMap;
import com.bc.file.MotifReader;

public class CISReader {
	private BufferedReader CISreader;
	private HashMap<String,String> elementMotif;
	private File patternFile;
	private PrintStream CISwriter;
	
	public CISReader(File patternFile, PrintStream writer) {
		this.patternFile = patternFile;
		buildCISMap();
		CISwriter = writer;
	}
	
	protected void buildCISMap() {
		elementMotif = new HashMap<String,String>();
		String motifFileName = "element_name_and_motif_IUPAC.txt";
		InputStream motifFile = getClass().getClassLoader().getResourceAsStream(motifFileName);
		MotifReader read = new MotifReader(motifFile);
		while (read.nextLine()) {
			elementMotif.put(read.getElement(), read.getMotif());
		}
	}
	
	public File getSignificantMotif() {
		File temp;
		PrintStream tempwrite;
		try {
			temp = File.createTempFile("motif", ".tmp");
			tempwrite = new PrintStream(temp);
		} catch (Exception e) {
			return null;
		}
		temp.deleteOnExit();
		if (!patternFile.exists()) {
			System.out.println("No motif file at " + patternFile.getName());
			return null;
		}
		String pattern = patternFile.getName();
		pattern = pattern.substring(0, pattern.lastIndexOf(".txt.processed.txt"));
		InputStream is;
		try  {
			is = new FileInputStream(patternFile);
		} catch (Exception e) {
			return null;
		}
		CISreader = new BufferedReader(new InputStreamReader(is));
		String nextLine = "";
		try {
			nextLine = CISreader.readLine();
		} catch (Exception e) {
			return null;
		}
		boolean sig = false;
		while (nextLine != null) {
			double pval = Double.parseDouble(nextLine.substring(nextLine.lastIndexOf('\t')).trim());
			if (pval < 0.001) {
				sig = true;
				String nextElement = nextLine.substring(0, nextLine.indexOf('\t'));
				String motif = elementMotif.get(nextElement.trim());
				CISwriter.println(pattern
						+ '\t'
						+ nextElement);
				tempwrite.println(nextElement.trim()
						+ '\t'
						+ motif);
			}
			try {
				nextLine = CISreader.readLine();
			} catch (Exception e) {
				return null;
			}
		}
		if (sig) {
			return temp;
		}
		else return null;
	}
}
