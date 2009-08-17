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
         backgroundChip = ces.processBackgroundChip(new FileInputStream(new File("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/src/main/resources/ATH1Chip.txt")));
      } catch (Exception e) {
      }
   }
/*
   public void testCounting() {
      PromomerService service = new PromomerServiceImpl();
      service.getCisCount(backgroundChip, new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/for siobhan/TAIR9_upstream_1000_20090619.txt"),
            "YACGTGGC", ResourceUtil.getFiles(
                  "C:/Documents and Settings/Corey Harada/Desktop/Mac/to_process"));
   }
*/
   public void testFile() {
	   System.out.println("Test Motif");
	   PromomerService service = new PromomerServiceImpl();
	   service.getMotifs(backgroundChip, 
			   new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/for siobhan/TAIR9_upstream_1000_20090619.txt"),
			   new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/test/test_Motif.txt"),
			   ResourceUtil.getFiles("C:/Documents and Settings/Corey Harada/Desktop/Mac/to_process"));
   }
}
