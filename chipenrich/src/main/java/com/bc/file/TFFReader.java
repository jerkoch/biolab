package com.bc.file;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.bc.core.AGI;
import com.bc.chipenrich.ui.locator.TFFLocator;

public class TFFReader {
	private HashMap<String,Set<AGI>> TFFMap;
	
	public TFFReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					TFFLocator.getInstance().getInputStream()));
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		TFFMap = new HashMap<String,Set<AGI>>();
		String nextLine = null;
		try {
			while ((nextLine = reader.readLine()) != null) {
				String[] nextLineSplit = nextLine.split("\t");
				String AGI_ID = nextLineSplit[0].trim();
				AGI nextAGI = AGI.createAGI(AGI_ID);
				String TFFName = nextLineSplit[1].trim().toUpperCase();
				Set<AGI> newSet;
				if (!TFFMap.containsKey(TFFName)) {
					newSet = new HashSet<AGI>();
					newSet.add(nextAGI);
					TFFMap.put(TFFName, newSet);
				}
				else {
					newSet = TFFMap.get(TFFName);
					newSet.add(nextAGI);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public Set<AGI> get(String arg) {
		return TFFMap.get(arg.toUpperCase());
	}
}
