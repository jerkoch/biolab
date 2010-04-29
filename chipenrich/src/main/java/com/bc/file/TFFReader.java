package com.bc.file;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.bc.core.AGI;
import com.bc.chipenrich.ui.chooser.PlantChooser;

public class TFFReader {
	private HashMap<String,Set<AGI>> TFFMap;
	private String loc = "/families_summary.txt";
	
	public TFFReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream(
							PlantChooser.getInstance().getPlant() + loc)));
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		TFFMap = new HashMap<String,Set<AGI>>();
		String nextLine = null;
		try {
//			nextLine = reader.readLine();	//Skip 1st line - header
			while ((nextLine = reader.readLine()) != null) {
				String[] nextLineSplit = nextLine.split("\t");
				String AGI_ID = nextLineSplit[0].trim();
//				String AGI_ID = nextLine.substring(0, nextLine.indexOf('\t')).trim();
				AGI nextAGI = AGI.createAGI(AGI_ID);
				String TFFName = nextLineSplit[1].trim();
//				String TFFName = nextLine.substring(nextLine.indexOf('\t') + 1).trim();
//				TFFName = TFFName.substring(0, TFFName.indexOf('\t')).trim();
				Set<AGI> newSet;
				if (!TFFMap.containsKey(TFFName)) {
					newSet = new HashSet<AGI>();
				}
				else {
					newSet = TFFMap.get(TFFName);
					TFFMap.remove(TFFName);
				}
				newSet.add(nextAGI);
				TFFMap.put(TFFName, newSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public Set<AGI> get(String arg) {
		return TFFMap.get(arg);
	}
}
