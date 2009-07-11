package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGIUpstream;
import com.bc.core.BackgroundChip;
import com.bc.file.UpstreamReader;
import com.bc.util.CisMatcherUtil;
import com.bc.util.DistributionCalculator;

public class PromomerServiceImpl implements PromomerService {

   public void getCisCount(BackgroundChip backgroundChip, File upstreamFile, String cis,
         File[] inputFiles) {
      try {
         Set[] queryList = new Set[inputFiles.length];
         for (int i = 0; i < inputFiles.length; i++) {
            System.out.println(inputFiles[i].getName());
            AGIQueryListParser parser = new AGIQueryListParser();
            parser.parse(new FileInputStream(inputFiles[i]));
            queryList[i] = parser.getAGIs();
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
                     if (found && queryList[i].contains(agiUp.getAgi()))
                        foundInFiles[i]++;
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
                  + queryList[i].size()
                  + "\t"
                  + foundBackground
                  + "\t"
                  + backgroundChip.getNumAGIs()
                  + "\t"
                  + DistributionCalculator.probabilty(backgroundChip.getNumAGIs(), foundBackground,
                        queryList[i].size(), foundInFiles[i]));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
