package com.bc.file;

import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class BindingSiteReader {
	private HashMap<String,String> TFFMap;
	
	public BindingSiteReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream("matching_binding_site_no_spaces_2.txt")));
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		TFFMap = new HashMap<String,String>();
		String nextLine = null;
		try {
			while ((nextLine = reader.readLine()) != null) {
				String elementName = nextLine.substring(0, nextLine.indexOf('\t')).trim();
				String TFFName = nextLine.substring(nextLine.indexOf('\t')).trim();
				TFFMap.put(elementName, TFFName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public String get(String arg) {
		return TFFMap.get(arg);
	}
}
