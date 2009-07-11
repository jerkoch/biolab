package com.bc.chipenrich.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.chipenrich.domain.BackgroundChipParser;
import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.chipenrich.domain.GOAnnotationParser;
import com.bc.chipenrich.domain.ProbabilityResult;
import com.bc.chipenrich.domain.ResultsHandler;
import com.bc.chipenrich.domain.TranscriptionFactorFamilyParser;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.core.GO;
import com.bc.core.GeneDescriptor;
import com.bc.core.GeneDescriptorMap;
import com.bc.core.TranscriptionFactorFamily;

public class ChipEnrichServiceImpl implements ChipEnrichService {

   public BackgroundChip processBackgroundChip(InputStream bcInputStream) {
      try {
         BackgroundChipParser backgroundChipParser = new BackgroundChipParser();
         backgroundChipParser.parse(bcInputStream);
         return backgroundChipParser.getBackgroundChip();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public GeneDescriptorMap<GO> processGoAnnotations(InputStream[] goAnnotationInputStream,
         BackgroundChip backgroundChip) {
      try {
         GOAnnotationParser goAnnoParser = new GOAnnotationParser();
         goAnnoParser.setAGIFilterSet(backgroundChip.getAGIs());
         for (int i = 0; i < goAnnotationInputStream.length; i++) {
            goAnnoParser.parse(goAnnotationInputStream[i]);
         }
         return goAnnoParser.getGeneDescriptorMap();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public GeneDescriptorMap<TranscriptionFactorFamily> processTranscriptionFactorFamily(
         InputStream tffSummaryInputStream, BackgroundChip backgroundChip) {
      try {
         TranscriptionFactorFamilyParser tffParser = new TranscriptionFactorFamilyParser();
         tffParser.setAGIFilterSet(backgroundChip.getAGIs());
         tffParser.parse(tffSummaryInputStream);
         return tffParser.getGeneDescriptorMap();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public EnrichmentSummary processEnrichment(BackgroundChip backgroundChip,
         GeneDescriptorMap<?> geneDescriptorMap, File[] inputFiles, String outputDirectory, boolean ignoreMultipleQLP) {

      try {
         EnrichmentSummary enrichmentSummary = new EnrichmentSummary();

         for (int i = 0; i < inputFiles.length; i++) {
            AGIQueryListParser qlp = new AGIQueryListParser();
            qlp.setDiscardMultipleAGIsOnSameLine(ignoreMultipleQLP);
            qlp.parse(new FileInputStream(inputFiles[i]));
            if (qlp.getAGIs().size() == 0) {
               System.out.println("Query list is empty: " + inputFiles[i]);
            }

            GeneDescriptorMap<?> filteredGeneDescriptorMap = geneDescriptorMap.subsetContainingValues(qlp.getAGIs());

            new File(outputDirectory).mkdirs();
            File outputFile = new File(outputDirectory, inputFiles[i].getName() + ".processed.txt");
            ResultsHandler outputter = new ResultsHandler(outputFile, backgroundChip);

            // -------- TEMP: Move into outputter
            File agiMatchFile = new File(outputDirectory, inputFiles[i].getName() + ".processed.agi_list.txt");
            PrintStream writer = new PrintStream(agiMatchFile);
            // ----------------------------------
            
            for (GeneDescriptor gd : filteredGeneDescriptorMap.getGeneDescriptors()) {

               int filteredCount = filteredGeneDescriptorMap.getAGIs(gd).size();
               int filteredSetSize = qlp.getAGIs().size();
               int geneDescCountOnBackground = geneDescriptorMap.getAGIs(gd).size();

               ProbabilityResult pr = ProbabilityResult.createProbabilityResult(gd, filteredCount,
                     filteredSetSize, geneDescCountOnBackground, backgroundChip.getNumAGIs());

               if (pr.isSignificant()) {

                  // -------- TEMP: Move into outputter
                  writer.print(gd + "\t" + filteredGeneDescriptorMap.getAGIs(gd).size());
                  for(AGI agi : filteredGeneDescriptorMap.getAGIs(gd)) {
                     writer.print("\t" + agi);
                  }
                  writer.println();
                  // ----------------------------------
                  
                  outputter.output(pr, filteredCount, filteredSetSize, geneDescCountOnBackground);
                  enrichmentSummary.add(inputFiles[i].getName(), pr);
               }
            }
            outputter.close();
            
            // -------- TEMP: Move into outputter
            writer.close();
            // ----------------------------------
         }
         return enrichmentSummary;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
