package com.bc.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MotifReader {
	
	private BufferedReader reader;
	private String nextMotif;
	private String nextElement;
	
	public MotifReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
		nextMotif = "";
		nextElement = "";
	}
	
	public boolean nextLine() {
		String line = "";
		try {
			line = reader.readLine();
			if (line == null)	//end of file
				return false;
			nextMotif = line.substring(line.indexOf('\t')).trim();
			nextElement = line.substring(0, line.lastIndexOf('\t')).trim();
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
	
	public String getMotif() {
		return nextMotif;
	}
	
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