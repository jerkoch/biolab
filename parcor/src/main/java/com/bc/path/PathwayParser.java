package com.bc.path;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import com.bc.common.core.AGI;
import com.bc.common.util.CSVParser;

public class PathwayParser {

	private CSVParser csvParser;
	private ExpressionMap expressionMap;
	private String[] tokens;

	public PathwayParser(InputStream is, ExpressionMap expressionMap) {
		this.expressionMap = expressionMap;
		csvParser = new CSVParser(is, "\t");
		csvParser.moreLines();
		tokens = csvParser.getTokens();
	}

	public Pathway nextPathway() {
		if ((tokens.length == 5) || (tokens.length == 6)) {
			String currentPathway = tokens[0];
			Pathway pathway = new Pathway(currentPathway);
			List<Step> steps = new ArrayList<Step>();
			while ((tokens.length >= 5) && (tokens[0].equals(currentPathway))) {
				boolean newStep = true;
				for (Step step : steps) {
					if (step.getReactionId().equals(tokens[2])) {
						newStep = false;
						step.addNode(AGI.createAGI(tokens[3]));
					}
				}
				if (newStep) {
					Step step;
					if (tokens.length == 5) {
						step = new Step(expressionMap, tokens[2], "", tokens[4]);
					}
					else {
						step = new Step(expressionMap, tokens[2], "", tokens[5]);
					}
					step.addNode(AGI.createAGI(tokens[3]));
					pathway.addStep(step);
					steps.add(step);
				}
				csvParser.moreLines();
				tokens = csvParser.getTokens();
			}
			return pathway;
		}
		return null;
	}
}
