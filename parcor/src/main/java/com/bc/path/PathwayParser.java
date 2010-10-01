package com.bc.path;

import java.io.InputStream;

import com.bc.common.core.AGI;
import com.bc.common.util.CSVParser;

public class PathwayParser {

	private CSVParser csvParser;
	private ExpressionMap expressionMap;

	public PathwayParser(InputStream is, ExpressionMap expressionMap) {
		this.expressionMap = expressionMap;
		csvParser = new CSVParser(is, "\t");
		csvParser.moreLines();
	}

	public Pathway nextPathway() {
		String tokens[] = csvParser.getTokens();
		if (tokens.length == 2) {
			Pathway pathway = new Pathway(tokens[0]);
			Step step = null;
			int numAGIs = Integer.parseInt(tokens[1]);
			for (int i = 0; csvParser.moreLines() && i < numAGIs ; i++) {
				tokens = csvParser.getTokens();
				if (step == null || !step.getReactionId().equals(tokens[1])) {
					step = new Step(expressionMap, tokens[1], tokens[2]);
					pathway.addStep(step);
				}
				step.addNode(AGI.createAGI(tokens[0]));
			}
			return pathway;
		}
		return null;
	}
}
