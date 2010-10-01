package com.bc.path;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

import com.bc.ui.locator.AffyATH1Locator;
import com.bc.ui.locator.ExpressionMapLocator;
import com.bc.ui.locator.PathwayLocator;

public class Launcher {
	public static void main(String[] args) {
		try {
			Options options = createOptions();
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);

			String expressionMapFilename = cmd.getOptionValue("ie");
			String affyath1Filename = cmd.getOptionValue("ia");
			String pathwayFilename = cmd.getOptionValue("ip");
			String outputFilename = cmd.getOptionValue("o");
			
			if (affyath1Filename != null) {
				AffyATH1Locator.getInstance().setExternalFile(new File(affyath1Filename));
			}
			
			if (expressionMapFilename != null) {
				ExpressionMapLocator.getInstance().setExternalFile(new File(expressionMapFilename));
			}
			
			if (pathwayFilename != null) {
				PathwayLocator.getInstance().setExternalFile(new File(pathwayFilename));
			}
			
			Runner.run(outputFilename, null);

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
