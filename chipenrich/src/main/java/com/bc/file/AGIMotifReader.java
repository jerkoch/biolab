package com.bc.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import com.bc.core.AGI;
import com.bc.core.AGIMotif;

/*
 * Reads from an AGI_Motif table.
 * The AGI_Motif table stores the number of times a motif appears in the given AGI.
 * 
 */
public class AGIMotifReader {
	private BufferedReader reader;
	private HashMap<String, Integer> motifCol;
	private int TableLength;	// The number of motifs in table
	private ArrayList<String> tableRow;	//Stores mapping of row # to AGI ID
	private HashMap<String, AGIMotif> AGIRow;	//Stores mapping of AGI ID to AGIMotif
	
	public AGIMotifReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
		try {
			motifCol = new HashMap<String, Integer>();
			AGIRow = new HashMap<String, AGIMotif>();
			//Parse the first line, getting motif names
			String firstLine = reader.readLine();
			String[] firstLineSplit = firstLine.split("\t");
			for (TableLength = 0; TableLength < firstLineSplit.length; TableLength++) {
				motifCol.put(firstLineSplit[TableLength], TableLength);
			}
			//Parse remaining lines, 
			String nextLine = "";
			tableRow = new ArrayList<String>();
			while ((nextLine = reader.readLine()) != null) {
				String[] nextLineSplit = nextLine.split("\t");
				int motifCount[] = new int[nextLineSplit.length + 1];
				for (int i = 1; i < nextLineSplit.length; i++) {
					motifCount[i] = Integer.parseInt(nextLineSplit[i]);
				}
				AGIMotif am = new AGIMotif(nextLineSplit[0], motifCount);
				tableRow.add(nextLineSplit[0]);
				AGIRow.put(nextLineSplit[0], am);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public int getMotifColumn(String motif) {
		if (motifCol.get(motif) != null)
			return motifCol.get(motif);
		else return -1;
	}
	
	public int getCount(String id, String motif) {
		if (getMotifColumn(motif) >= 0) {
			if (AGIRow.containsKey(id)) {
				return AGIRow.get(id).getMotifCount(getMotifColumn(motif));
			}
			return -1;	//AGI id not found
		}
		else return -2;	//Motif not found
	}
	
	public AGI getAGIat(int index) {
		return AGI.createAGI(tableRow.get(index));
	}
	
	public int numAGIs() {
		return AGIRow.size();
	}
}
