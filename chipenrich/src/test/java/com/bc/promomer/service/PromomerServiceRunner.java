package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.core.BackgroundChip;
import com.bc.util.ResourceUtil;

public class PromomerServiceRunner extends TestCase {

   private BackgroundChip backgroundChip;
   {
      try {
         ChipEnrichService ces = new ChipEnrichServiceImpl();
         backgroundChip = ces.processBackgroundChip(new FileInputStream(new File("D:/Siobhan/ATH1Chip.txt")));
      } catch (Exception e) {
      }
   }

   public void testCounting() {
      PromomerService service = new PromomerServiceImpl();
      service.getCisCount(backgroundChip, new File("D:/Siobhan/TAIR6_upstream_1000_20060310"),
            "AACGG", ResourceUtil.getFiles(
                  "D:/Siobhan/to_process"));
   }
}
