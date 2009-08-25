package com.bc.promomer.service;

import java.io.File;
import java.io.PrintStream;
import java.util.Set;

import com.bc.core.AGI;

public interface PromomerTable {
	public void getCisCount(String motifFileName,
			File inputFile, String outputDir);
	
	public double parseLine(String nextMotif, String nextElement, 
			Set<AGI> queryList, PrintStream printer, 
			PrintStream printerAGI);
}
