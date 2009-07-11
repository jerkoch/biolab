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

   public AbstractRunner(JLabel status, ChipEnrichService ces, File[] queryFiles, String root,
         String baseOutputDir, String backgroundChipFilename, boolean ignoreMultipleQLP) {
      setPriority(Thread.NORM_PRIORITY - 1);
      this.statusLabel = status;
      this.ces = ces;
      this.queryFiles = queryFiles;
      this.root = root;
      this.baseOutputDir = baseOutputDir;
      this.ignoreMultipleQLP = ignoreMultipleQLP;
      this.backgroundChipFilename = backgroundChipFilename;
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

      statusLabel.setText(getRunnerName() + ": Processing GOs...");
      gdMap = ces.processGoAnnotations(GOAnnotationLocator.getInstance().getInputStreams(),
            backgroundChip);
      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "go", gdMap);

      statusLabel.setText(getRunnerName() + ": Processing Arrays...");
      gdMap = ces.processTranscriptionFactorFamily(getClass().getClassLoader().getResourceAsStream(
            "ArrayAnnotationSummary.txt"), backgroundChip);
      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "array", gdMap);

      statusLabel.setText(getRunnerName() + ": Processing TFFs...");
      gdMap = ces.processTranscriptionFactorFamily(getClass().getClassLoader().getResourceAsStream(
            "families_summary.txt"), backgroundChip);
      processEnrichment(ces, backgroundChip, queryFiles, baseOutputDir, "tff", gdMap);
   }

   public void processEnrichment(ChipEnrichService ces, BackgroundChip backgroundChip,
         File[] queryFiles, String baseDir, String subDir, GeneDescriptorMap<?> descriptorMap) {

      String outputDir = baseDir + "/" + root + "/" + subDir;
      EnrichmentSummary summary = ces.processEnrichment(backgroundChip, descriptorMap, queryFiles,
            outputDir, ignoreMultipleQLP);
      ResultsHandler.outputSummary(new File(outputDir, "summary.txt"), summary);
   }

   protected abstract String getRunnerName();
}
