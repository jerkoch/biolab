package com.bc.path;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

import com.bc.common.core.AGI;
import com.bc.common.core.Affy;
import com.bc.common.util.CSVParser;
import com.bc.common.util.ParsingError;

public class AffyAgiParser {

	private MultiMap<Affy, AGI> affyMap;

	private MultiMap<AGI, Affy> agiMap;

	private List<ParsingError> errors;

	public AffyAgiParser(InputStream is) {
		parse(is);
	}

	private void parse(InputStream is) {
		affyMap = new MultiHashMap<Affy, AGI>();
		agiMap = new MultiHashMap<AGI, Affy>();
		errors = new ArrayList<ParsingError>();

		int i = 0;
		CSVParser csvParser = new CSVParser(is, "\t");
		while (csvParser.moreLines()) {
			i++;
			String affyStr = csvParser.nextToken();
			csvParser.nextToken();
			csvParser.nextToken();
			csvParser.nextToken();
			String agiStr = csvParser.nextToken();

			try {
				String vals[] = agiStr.split(";");
				for (int j = 0; j < vals.length; j++) {
					AGI agi = AGI.createAGI(vals[j]);
					Affy affy = Affy.createAffy(affyStr);
					affyMap.put(affy, agi);
					agiMap.put(agi, affy);
				}
			} catch (Exception e) {
				errors.add(ParsingError.createParsingError(i, e.getMessage()));
			}
		}

	}

	public Collection<AGI> getAGIs(Affy affy) {
		return affyMap.get(affy);
	}

	public Affy getAffy(AGI agi) {
		Collection<Affy> affys = agiMap.get(agi);
		if (affys != null && affys.size() == 1) { 
			Collection<AGI> agis = affyMap.get(affys.toArray()[0]);
			if (agis.size() == 1) {
				return affys.iterator().next();
			}
		}
		return null;
	}

	public List<ParsingError> getErrors() {
		return errors;
	}
}
