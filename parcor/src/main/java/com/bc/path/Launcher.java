package com.bc.path;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class Launcher {

	private static final String DEFAULT_AFFY_AGI_FILE = "affy_ATH1_array_elements-2008-5-29.txt";
	private static final String DEFAULT_EXP_MAP_FILE = "fivemarkerlines.txt";
	private static final String DEFAULT_PATHWAY_FILE = "GeneEachPathwayAll.txt";


	public static void main(String[] args) {

		try {
			Options options = createOptions();
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);

			String expressionMapFilename = cmd.getOptionValue("ie");
			String affyath1Filename = cmd.getOptionValue("ia");
			String pathwayFilename = cmd.getOptionValue("ip");
			
			// parse the affy-agi file so we can retrieve the mapping
			AffyAgiParser affyAgiParser;
			if (affyath1Filename == null) {
				 affyAgiParser = new AffyAgiParser(ClassLoader.getSystemResourceAsStream(DEFAULT_AFFY_AGI_FILE));	
			} else {
				 affyAgiParser = new AffyAgiParser(new FileInputStream(new File(affyath1Filename)));	
			}
			
			ExpressionMap expressionMap;
			if (expressionMapFilename == null) {
				expressionMap = new ExpressionMap(ClassLoader.getSystemResourceAsStream(DEFAULT_EXP_MAP_FILE), affyAgiParser);	
			} else {
				expressionMap = new ExpressionMap(new FileInputStream(new File(affyath1Filename)), affyAgiParser);	
			}
			
			PathwayParser pathwayParser;
			if (pathwayFilename == null) {
				pathwayParser = new PathwayParser(ClassLoader.getSystemResourceAsStream(DEFAULT_PATHWAY_FILE), expressionMap);
			} else {
				pathwayParser = new PathwayParser(new FileInputStream(new File(pathwayFilename)), expressionMap);
			}
			
			System.out.println(pathwayParser.nextPathway());
			System.out.println(pathwayParser.nextPathway());
			System.out.println(pathwayParser.nextPathway());
			System.out.println(pathwayParser.nextPathway());
			System.out.println(pathwayParser.nextPathway());
			System.out.println(pathwayParser.nextPathway());
			
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static Options createOptions() {
		// create Options object
		Options options = new Options();

		// add options
		options.addOption("ie", true, "expression data input");
		options.addOption("ia", true, "affy/ath1 input");
		options.addOption("ip", true, "pathway input");
		options.addOption("o", true, "output file (exclude to output console)");
		return options;
	}
}
