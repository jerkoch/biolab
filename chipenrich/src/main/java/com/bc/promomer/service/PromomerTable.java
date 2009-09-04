package com.bc.promomer.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

import com.bc.core.AGI;
import com.bc.chipenrich.domain.EnrichmentSummary;

/* 
 * An interface to do CIS analysis via the precompiled table
 */
public interface PromomerTable {
	/*
	 * Outputs the motif analysis based on an input file
	 */
	public void getCisCount(File inputFile, String outputDir, EnrichmentSummary summary);
	
	/*
	 * Parses a motif given a querylist.  printer and printerAGI may be null.
	 * Returns the pvalue of the association.
	 */
	public double parseLine(String patternName, String nextMotif, String nextElement, 
			Set<AGI> queryList, PrintWriter printer, 
			PrintWriter printerAGI);
}
