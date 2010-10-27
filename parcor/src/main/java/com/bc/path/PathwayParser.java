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
		//Build a pathway from the input, Pathway finds best path
		/*
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
		*/
		
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
