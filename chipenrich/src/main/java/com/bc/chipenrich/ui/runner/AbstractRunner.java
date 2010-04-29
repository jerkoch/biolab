/**
 * 
 */
package com.bc.chipenrich.ui.runner;

import java.io.File;

import javax.swing.JLabel;

import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.chipenrich.domain.ResultsHandler;
import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.GOAnnotationLocator;
import com.bc.chipenrich.ui.chooser.PlantChooser;
import com.bc.core.BackgroundChip;
import com.bc.core.GeneDescriptorMap;

/**
 * @author Jeremy Koch
 */
public abstract class AbstractRunner extends Thread {

   private ChipEnrichService ces;
   private String backgroundChipFilename;
   private File[] queryFiles;
   private String baseOutputDir;
   private boolean ignoreMultipleQLP;
   private String root;
   private JLabel statusLabel;
   
   private boolean runGO;
   private boolean runArray;
   private boolean runTFF;
   private boolean runMetabolics;

   public AbstractRunner(JLabel status, ChipEnrichService ces, File[] queryFiles, String root,
         String baseOutputDir, String backgroundChipFilename, boolean ignoreMultipleQLP,
         boolean runGO, boolean runArray, boolean runTFF, boolean runMetabolics) {
      setPriority(Thread.NORM_PRIORITY - 1);
      this.statusLabel = status;
      this.ces = ces;
      this.queryFiles = queryFiles;
      this.root = root;
      this.baseOutputDir = baseOutputDir;
      this.ignoreMultipleQLP = ignoreMultipleQLP;
      this.backgroundChipFilename = backgroundChipFilename;
      
      this.runGO = runGO;
      this.runArray = runArray;
      this.runTFF = runTFF;
      this.runMetabolics = runMetabolics;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Thread#run()
    */
   @Override
   public void run() {

      GeneDescriptorMap<?> gdMap;

      statusLabel.setText(getRunnerName() + ": Processing background chip...");
      BackgroundChip backgroundChip = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream(
            backgroundChipFilename));

      if (runGO) {
	      statusLabel.setText(getRunnerName() + ": Processing GOs...");
	      gdMap = ces.processGoAnnotations(GOAnnotationLocator.getInstance().getInputStreams(),
	            backgroundChip);
	      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "go", gdMap);
      }
      
      if (runArray) {
	      statusLabel.setText(getRunnerName() + ": Processing Arrays...");
	      gdMap = ces.processTranscriptionFactorFamily(getClass().getClassLoader().getResourceAsStream(
	            PlantChooser.getInstance().getPlant() + "/ArrayAnnotationSummary.txt"), backgroundChip);
	      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "array", gdMap);
      }
      
      if (runTFF) {
	      statusLabel.setText(getRunnerName() + ": Processing TFFs...");
	      gdMap = ces.processTranscriptionFactorFamily(getClass().getClassLoader().getResourceAsStream(
	            PlantChooser.getInstance().getPlant() + "/families_summary.txt"), backgroundChip);
	      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "tff", gdMap);
      }
      
      if (runMetabolics) {
	      statusLabel.setText(getRunnerName() + ": Processing Metabolics...");
	      gdMap = ces.processTranscriptionFactorFamily(getClass().getClassLoader().getResourceAsStream(
	            PlantChooser.getInstance().getPlant() + "/metabolicpathways2008.txt"), backgroundChip);
	      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "metabolic", gdMap);
      }
   }

   public void processEnrichment(ChipEnrichService ces, BackgroundChip backgroundChip,
         File[] queryFiles, String baseDir, String subDir, GeneDescriptorMap<?> descriptorMap) {
      String outputDir = baseDir + "/" + root + "/" + subDir;
      EnrichmentSummary summary = ces.processEnrichment(backgroundChip, descriptorMap, queryFiles,
            outputDir, ignoreMultipleQLP);
      if (summary != null) {
    	  ResultsHandler.outputSummary(new File(outputDir, "summary.txt"), summary);
      }
   }

   protected abstract String getRunnerName();
}
