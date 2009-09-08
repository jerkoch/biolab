package com.bc.chipenrich.domain;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import com.bc.core.BackgroundChip;
import com.bc.core.GeneDescriptor;

public class ResultsHandler {

   private BackgroundChip backgroundChip;
   private PrintStream writer;

   public ResultsHandler(OutputStream outputStream, BackgroundChip backgroundChip) {
      this.writer = new PrintStream(outputStream);
      this.backgroundChip = backgroundChip;
   }

   public ResultsHandler(File outputFile, BackgroundChip backgroundChip) {
      try {
         writer = new PrintStream(outputFile);
      } catch (Exception e) {
      }
      this.backgroundChip = backgroundChip;
   }

   public void output(ProbabilityResult probabilityResult, int filteredCount, int filterSetSize,
         int geneDescCountOnBackground, String agis) {

      // output the results line
      writer.print(probabilityResult.getGeneDescriptor().getId());
      writer.print("\t");
      writer.print(probabilityResult.getGeneDescriptor().getDescription());
      writer.print("\t");
      writer.print(filteredCount);
      writer.print("\t");
      writer.print(filterSetSize);
      writer.print("\t");
      writer.print(geneDescCountOnBackground);
      writer.print("\t");
      writer.print(backgroundChip.getNumAGIs());
      writer.print("\t");
      writer.print(probabilityResult.getProbability());
      writer.print(agis);
      writer.println();
   }

   public static void outputSummary(File outputFile, EnrichmentSummary enrichmentSummary) {
      try {
         PrintStream writer = new PrintStream(outputFile);

         writer.print("Gene Descriptor\tDescription");
         for (String setName : enrichmentSummary.getSetNames()) {
            writer.print("\t" + setName);
         }
         writer.println("");

         for (GeneDescriptor gd : enrichmentSummary.getGeneDescriptors()) {
            if (enrichmentSummary.isSignificant(gd)) {
               writer.print(gd.getId() + "\t" + gd.getDescription());
               for (String setName : enrichmentSummary.getSetNames()) {
                  ProbabilityResult pr = enrichmentSummary.getProbabilityResult(setName, gd);
                  writer.print("\t" + (pr == null ? "0" : Math.log10(pr.getProbability())));
               }
               writer.println("");
            }
         }
         writer.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void close() {
      try {
         writer.close();
      } catch (Exception e) {
      }
   }
}
