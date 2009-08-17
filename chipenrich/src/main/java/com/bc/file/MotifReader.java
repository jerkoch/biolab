package com.bc.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MotifReader {
	
	private BufferedReader reader;
	
	public MotifReader(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
	}
	
	public String getMotif() {
		String line = "";
		try {
			line = reader.readLine();
			if (line == null)	//end of file
				return null;
			return line.substring(line.indexOf('\t')).trim();
		} catch (Exception e) {
			System.out.println(line);
			System.out.print(line.indexOf('\t'));
			System.out.print('\n');
			System.out.print(line.lastIndexOf(' '));
			System.out.print('\n');
			e.printStackTrace();
			System.exit(1);
			return null;
		}
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