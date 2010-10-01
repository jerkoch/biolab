package com.bc.path;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.text.StrBuilder;

import com.bc.common.core.AGI;
import com.bc.common.core.Affy;
import com.bc.common.util.CSVParser;
import com.bc.common.util.ParsingError;

public class ExpressionMap {

	private AffyAgiParser converter;
	private Map<Affy, Double[]> map;
	private List<ParsingError> errors;

	public ExpressionMap(InputStream is, AffyAgiParser converter) {
		this.converter = converter;
		parse(is);
	}

	private void parse(InputStream is) {
		map = new TreeMap<Affy, Double[]>();
		errors = new ArrayList<ParsingError>();

		CSVParser csvParser = new CSVParser(is, "\t");
		int i = 0;
		while (csvParser.moreLines()) {
			i++;
			try {
				Affy affy = Affy.createAffy(csvParser.nextToken());
				Double[] data = new Double[0];
				String[] dataStr = csvParser.getTokens();
				for (int j = 0; j < dataStr.length; j++) {
					data = (Double[]) ArrayUtils.add(data, Double.valueOf(dataStr[j]));
				}
				map.put(affy, data);
			} catch (Exception e) {
				errors.add(ParsingError.createParsingError(i, e.getMessage()));
			}
		}
	}
	
	public Double[] getExpressionData(AGI agi) {
		Affy affy = converter.getAffy(agi);
		if (affy != null) {
			return map.get(affy);
		} else {
			return null;
		}
	}

	public void output(OutputStream out) {
		PrintStream ps = new PrintStream(out);
		for (Affy affy : map.keySet()) {
			StrBuilder sb = new StrBuilder();
			sb.append(affy);
			sb.append('\t');
			sb.appendWithSeparators(converter.getAGIs(affy), ";");
			sb.append('\t');
			sb.appendWithSeparators(map.get(affy), "\t");
			sb.appendNewLine();
			ps.print(sb.toString());
		}

		StrBuilder sb = new StrBuilder();
		sb.appendNewLine();
		for (ParsingError error : errors) {
			sb.appendln(error);
		}
		ps.print(sb.toString());
	}
}
