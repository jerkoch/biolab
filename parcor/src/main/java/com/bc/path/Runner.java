package com.bc.path;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import com.bc.ui.MainWindow;
import com.bc.ui.locator.AffyATH1Locator;
import com.bc.ui.locator.ExpressionMapLocator;
import com.bc.ui.locator.PathwayLocator;

public class Runner {
	
	public static void run(String outputFilename, MainWindow main) {
		try {
			// parse the affy-agi file so we can retrieve the mapping
			AffyAgiParser affyAgiParser = new AffyAgiParser(AffyATH1Locator.getInstance().getInputStream());	
	
			ExpressionMap expressionMap = new ExpressionMap(ExpressionMapLocator.getInstance().getInputStream(), affyAgiParser);	
	
			PathwayParser pathwayParser = new PathwayParser(PathwayLocator.getInstance().getInputStream(), expressionMap);
			
			OutputStreamWriter writer;
			if (outputFilename == null) {
				writer = new OutputStreamWriter(System.out);
			} else {
				writer = new OutputStreamWriter(new FileOutputStream(outputFilename));
			}
	
			if (main != null) {
				main.printMessage("Correlation Mode: " + Node.getCorrelation() + "\n\n");
			}
			writer.write("Correlation Mode: " + Node.getCorrelation() + "\n\n");
			
			Pathway nextPathway = pathwayParser.nextPathway();
			while (nextPathway != null) {
				if (main != null) {
					main.printMessage(nextPathway.toString());
					main.printMessage("\n");
				}
				writer.write(nextPathway.toString());
				writer.write("\n");
				nextPathway = pathwayParser.nextPathway();
			}
			writer.close();
			System.out.println("Done");
			
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.getMessage());
			e.printStackTrace();
			if (main != null) {
				main.printMessage("Unexpected exception: " + e.getMessage() + "\n");
			}
		}
	}
}
