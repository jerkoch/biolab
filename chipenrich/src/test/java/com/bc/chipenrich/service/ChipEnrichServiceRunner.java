package com.bc.chipenrich.service;

import java.io.File;
import java.io.FileFilter;

import junit.framework.TestCase;

import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.chipenrich.domain.ResultsHandler;
import com.bc.core.BackgroundChip;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;
import com.bc.core.TranscriptionFactorFamily;
import com.bc.util.ResourceUtil;

public class ChipEnrichServiceRunner extends TestCase {

   private String processDir = "D:/Siobhan/to_process/";
   private ChipEnrichService ces;
   private BackgroundChip backgroundChip;
   {
      ces = new ChipEnrichServiceImpl();
      backgroundChip = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream(
            "ATH1Chip.txt"));
   }

   public void testGO() throws Exception {
      GeneDescriptorMap<GO> gdMap = ces.processGoAnnotations(ResourceUtil.getInputStreams(
            getClass().getClassLoader(), new String[] { "GOAnnotations1.txt", "GOAnnotations2.txt",
                  "GOAnnotations3.txt", "GOAnnotations4.txt", "GOAnnotations5.txt" }),
            backgroundChip);

      File[] inDirs = getInputDirectories();
      for (int i = 0; i < inDirs.length; i++) {
         String outputDir = inDirs[i].getAbsolutePath() + "/ath1chip/go";
         EnrichmentSummary summary = ces.processEnrichment(backgroundChip, gdMap,
               ResourceUtil.getFiles(inDirs[i].getAbsolutePath()), outputDir, false, "go");
         ResultsHandler.outputSummary(new File(outputDir, "summary.txt"), summary);
      }
   }

   public void testArray() throws Exception {
      GeneDescriptorMap<TranscriptionFactorFamily> gdMap = ces.processTranscriptionFactorFamily(
            getClass().getClassLoader().getResourceAsStream("ArrayAnnotationSummary.txt"),
            backgroundChip);

      File[] inDirs = getInputDirectories();
      for (int i = 0; i < inDirs.length; i++) {
         String outputDir = inDirs[i].getAbsolutePath() + "/ath1chip/array";
         EnrichmentSummary summary = ces.processEnrichment(backgroundChip, gdMap,
               ResourceUtil.getFiles(inDirs[i].getAbsolutePath()), outputDir, false, "array");
         ResultsHandler.outputSummary(new File(outputDir, "summary.txt"), summary);
      }
   }

   public void testTFF() throws Exception {
      GeneDescriptorMap<TranscriptionFactorFamily> gdMap = ces.processTranscriptionFactorFamily(
            getClass().getClassLoader().getResourceAsStream("families_summary.txt"), backgroundChip);

      File[] inDirs = getInputDirectories();
      for (int i = 0; i < inDirs.length; i++) {
         String outputDir = inDirs[i].getAbsolutePath() + "/ath1chip/tff";
         EnrichmentSummary summary = ces.processEnrichment(backgroundChip, gdMap,
               ResourceUtil.getFiles(inDirs[i].getAbsolutePath()), outputDir, false, "tff");
         ResultsHandler.outputSummary(new File(outputDir, "summary.txt"), summary);
      }
   }

   private File[] getInputDirectories() {
      File[] dirs = new File(processDir).listFiles(new FileFilter() {
         public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
               return true;
            }
            return false;
         }
      });
      return dirs;
   }
}
