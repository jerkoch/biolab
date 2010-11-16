package com.bc.file;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.bc.chipenrich.ui.locator.BindingSiteLocator;

public class BindingSiteReader {
	private HashMap<String,Set<String>> TFFMap;
	
	public BindingSiteReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					BindingSiteLocator.getInstance().getInputStream()));
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		TFFMap = new HashMap<String,Set<String>>();
		String nextLine = null;
		try {
			while ((nextLine = reader.readLine()) != null) {
				String elementName = nextLine.substring(0, nextLine.indexOf('\t')).trim();
				String TFFName = nextLine.substring(nextLine.indexOf('\t')).trim();
				if (TFFMap.containsKey(elementName)) {
					TFFMap.get(elementName).add(TFFName);
				}
				else {
					TreeSet<String> newTFF = new TreeSet<String>();
					newTFF.add(TFFName);
					TFFMap.put(elementName, newTFF);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public Set<String> get(String arg) {
		return TFFMap.get(arg);
	}
}
