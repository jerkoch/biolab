package com.bc.misc;

import java.io.File;
import java.io.FileInputStream;

import com.bc.chipenrich.domain.GOAnnotationParser;
import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.core.AGI;
import com.bc.core.BackgroundChip;
import com.bc.core.GO;
import com.bc.util.ResourceUtil;

public class GOOutputter {

   /**
    * @param args
    */
   public static void main(String[] args) {
//      GOOutputter goOutputter = new GOOutputter();
//      goOutputter.outputGOAssociation(new File("D:/Siobhan/ATH1Chip.txt"));
//      goOutputter.outputGOAssociation(new File("D:/Siobhan/SINGLETONS.txt"));
//      goOutputter.checkSingletonsSubsetOfAth1Chip();
   }

   public void outputGOAssociation(File background) throws Exception {
      
      ChipEnrichService ces = new ChipEnrichServiceImpl();
      BackgroundChip backgroundChip = ces.processBackgroundChip(new FileInputStream(background));

      File[] goAnnotationFiles = ResourceUtil.getFiles("D:/Siobhan/", "GOAnno");
      try {
         GOAnnotationParser goAnnoParser = new GOAnnotationParser();
         goAnnoParser.setAGIFilterSet(backgroundChip.getAGIs());
         for (int i = 0; i < goAnnotationFiles.length; i++) {
            goAnnoParser.parse(new FileInputStream(goAnnotationFiles[i]));
         }

         for (AGI agi : goAnnoParser.getAGIMap().keySet()) {
            for (GO go : goAnnoParser.getAGIMap().get(agi)) {
               System.out.println(agi + "\t" + go + "\t" + go.getDescription());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void checkSingletonsSubsetOfAth1Chip() throws Exception {
      ChipEnrichService ces = new ChipEnrichServiceImpl();
      BackgroundChip backgroundChip = ces.processBackgroundChip(new FileInputStream(new File("D:/Siobhan/ATH1Chip.txt")));
      BackgroundChip singletons = ces.processBackgroundChip(new FileInputStream(new File("D:/Siobhan/SINGLETONS.txt")));

      System.out.println("numAGIs in ath1chip=" + backgroundChip.getNumAGIs());
      System.out.println("numAGIs in singletons=" + singletons.getNumAGIs());
      for (AGI agi : singletons.getAGIs()) {
         if (!backgroundChip.getAGIs().contains(agi)) {
            System.out.println("Not found in ATH1Chip=" + agi);
         }
      }
      System.out.println("done!");
   }
}
