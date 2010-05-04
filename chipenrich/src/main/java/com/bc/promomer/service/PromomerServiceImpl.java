package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;
import java.util.Vector;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGIUpstream;
import com.bc.core.BackgroundChip;
import com.bc.core.AGI;
import com.bc.file.UpstreamReader;
import com.bc.util.CisMatcherUtil;
import com.bc.util.DistributionCalculator;
import com.bc.file.MotifReader;

public class PromomerServiceImpl implements PromomerService {
   public void getMotifs(BackgroundChip backgroundChip, File upstreamFile, File motifFile,
		 File[] inputFiles) {
	   try {
		   MotifReader reader = new MotifReader(new FileInputStream(motifFile));
		   String nextMotif;
		   while ((nextMotif = reader.getMotif()) != null) {
			   getCisCount(backgroundChip, upstreamFile, nextMotif, inputFiles);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }
	
   public void getCisCount(BackgroundChip backgroundChip, File upstreamFile, String cis,
         File[] inputFiles) {
      try {
         Vector<Set<AGI>> queryList = new Vector<Set<AGI>>(inputFiles.length);
         for (int i = 0; i < inputFiles.length; i++) {
            System.out.println(inputFiles[i].getName());
            AGIQueryListParser parser = new AGIQueryListParser();
            parser.parse(new FileInputStream(inputFiles[i]));
            queryList.add(i, parser.getAGIs());
         }

         UpstreamReader reader = new UpstreamReader(new FileInputStream(upstreamFile));
         AGIUpstream agiUp;
         int foundBackground = 0;
         int[] foundInFiles = new int[inputFiles.length];

         try {
            while ((agiUp = reader.nextAGIUpstream()) != null) {
               boolean found = false;
               if (backgroundChip.getAGIs().contains(agiUp.getAgi())) {
                  if (CisMatcherUtil.getCisCount(cis, agiUp.getUpstream()) > 0) {
                     foundBackground++;
                     found = true;
                  }
                  for (int i = 0; i < inputFiles.length; i++) {
                     if (found && queryList.elementAt(i).contains(agiUp.getAgi())) {
//                    	 System.out.println(agiUp.getAgi());
                        foundInFiles[i]++;
                     }
                  }
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

         for (int i = 0; i < inputFiles.length; i++) {
            System.out.println(inputFiles[i].getName()
                  + "\t"
                  + cis
                  + "\t"
                  + foundInFiles[i]
                  + "\t"
                  + queryList.elementAt(i).size()
                  + "\t"
                  + foundBackground
                  + "\t"
                  + backgroundChip.getNumAGIs()
                  + "\t"
                  + DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
                        queryList.elementAt(i).size(), foundInFiles[i]));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
