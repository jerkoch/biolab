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
         int geneDescCountOnBackground) {

      // output the results line
      writer.println(probabilityResult.getGeneDescriptor().getId() + "\t"
            + probabilityResult.getGeneDescriptor().getDescription() + "\t" + filteredCount + "\t"
            + filterSetSize + "\t" + geneDescCountOnBackground + "\t" + backgroundChip.getNumAGIs()
            + "\t" + probabilityResult.getProbability());
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
