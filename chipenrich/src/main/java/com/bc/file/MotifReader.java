package com.bc.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Class for reading motif files.
 * Format: Tab delimited file with element name in col 1 and IUPAC sequence in col 2
 * Usage:	Call nextLine() and use getMotif() and getElement()
 * 			Call nextLine() again to get next motif and element
 * 			nextLine() returns false when all Motifs have been read.
 */
public class MotifReader {
	
	private BufferedReader reader;
	private String nextMotif;
	private String nextElement;
	
	public MotifReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
		nextMotif = null;
		nextElement = null;
	}
	
	/*
	 * Updates value returned for getMotif() and getElement()
	 * Returns false when there are no more motifs to read.
	 */
	public boolean nextLine() {
		String line = "";
		try {
			line = reader.readLine();
			if (line == null)	//end of file
				return false;
			String[] lines = line.split("\t", 3);
			nextElement = lines[0].trim();
			nextMotif = lines[1].trim();
			return true;
		} catch (Exception e) {
			System.out.println(line);
			System.out.print(line.indexOf('\t'));
			System.out.print('\n');
			System.out.print(line.lastIndexOf(' '));
			System.out.print('\n');
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}
	
	/*
	 * Returns IUPAC motif
	 * Changes value when nextLine() is called
	 */
	public String getMotif() {
		return nextMotif;
	}
	
	/*
	 * Returns name of element
	 * Changes value when nextLine() is called
	 */
	public String getElement() {
		return nextElement;
	}
	
	public void close() {
		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
	}
}