package com.bc.cisanalysis;
//Deprecated
/*
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.bc.core.GO;

public class GOReader {
	private BufferedReader reader;
	
	public GOReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
	}
	
	public GO getSignificantGO() {
		String nextLine = "";
		try {
			while ((nextLine = reader.readLine()) != null) {
				double pval = Double.parseDouble(nextLine.substring(nextLine.lastIndexOf('\t')).trim());
				if (pval < 0.001) {
					String goId = nextLine.substring(0, nextLine.indexOf('\t')).trim();
					nextLine = nextLine.substring(nextLine.indexOf('\t') + 1).trim();
					String goDesc = nextLine.substring(0, nextLine.indexOf('\t')).trim();
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
*/