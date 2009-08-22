package com.bc.cisanalysis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.bc.core.GeneDescriptorMap;
import com.bc.core.GO;
import com.bc.core.AGI;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

public class GOagiReader {
	private GeneDescriptorMap<GO> GDMap;
	
	public GOagiReader(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String nextLine = "";
		Map<GO, Set<AGI>> mapping = new HashMap<GO, Set<AGI>>();
		try {
			while ((nextLine = reader.readLine()) != null) {
				String[] nextRow = nextLine.split("\t");
				GO nextGO = GO.createGO(nextRow[0]);
				Set<AGI> agis = new TreeSet<AGI>();
				for (int i = 2; i < nextRow.length; i++) {
					agis.add(AGI.createAGI(nextRow[i]));
				}
				mapping.put(nextGO, agis);
			}
			GDMap = new GeneDescriptorMap<GO>(mapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<AGI> getAGIs(GO go) {
		return GDMap.getAGIs(go);
	}
}
