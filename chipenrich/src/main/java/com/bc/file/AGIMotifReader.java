package com.bc.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;
import com.bc.core.AGI;
import com.bc.core.AGIMotif;

public class AGIMotifReader {
	private BufferedReader reader;
	private HashMap<String, Integer> motifCol;
	private int TableLength;
	private Vector<AGIMotif> tableRow;
	
	public AGIMotifReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
		try {
			motifCol = new HashMap<String, Integer>();
			//Parse the first line, getting motif names
			String firstLine = reader.readLine();
			String[] firstLineSplit = firstLine.split("\t");
			for (TableLength = 0; TableLength < firstLineSplit.length; TableLength++) {
				motifCol.put(firstLineSplit[TableLength], TableLength);
			}
			//Parse remaining lines, 
			String nextLine = "";
			tableRow = new Vector<AGIMotif>();
			while ((nextLine = reader.readLine()) != null) {
				String[] nextLineSplit = nextLine.split("\t");
				AGI newAGI = AGI.createAGI(nextLineSplit[0]);
				int motifCount[] = new int[nextLineSplit.length + 1];
				for (int i = 1; i < nextLineSplit.length; i++) {
					motifCount[i] = Integer.parseInt(nextLineSplit[i]);
				}
				AGIMotif am = new AGIMotif(newAGI, motifCount);
				tableRow.add(am);
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
	
	public int getCount(AGI id, String motif) {
		if (getMotifColumn(motif) >= 0) {
			for (int i = 0; i < tableRow.size(); i++) {
				if (tableRow.elementAt(i).getAGI().getId().equals(id.getId())) {
					return tableRow.elementAt(i).getMotifCount(getMotifColumn(motif));
				}
			}
			return -1;	//AGI id not found
		}
		else return -2;	//Motif not found
	}
	
	public AGI getAGIat(int index) {
		return tableRow.elementAt(index).getAGI();
	}
	
	public int numAGIs() {
		return tableRow.size();
	}
}
