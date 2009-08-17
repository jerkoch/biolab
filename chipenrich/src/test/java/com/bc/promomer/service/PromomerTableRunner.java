package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.core.BackgroundChip;
import com.bc.util.ResourceUtil;

public class PromomerTableRunner extends TestCase{
   private BackgroundChip backgroundChip;
   {
      try {
         ChipEnrichService ces = new ChipEnrichServiceImpl();
         backgroundChip = ces.processBackgroundChip(new FileInputStream(new File("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/src/main/resources/ATH1Chip.txt")));
      } catch (Exception e) {
      }
   }
   public void testFile() {
	   System.out.println("Test Table");
	   PromomerTable service = new PromomerTableImpl();
	   service.getCisCount(backgroundChip, 
			   new File("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/AGI_Motif_Table.txt"),
			   new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/test/test_Motif.txt"),
			   ResourceUtil.getFiles("C:/Documents and Settings/Corey Harada/Desktop/Mac/to_process"));
   }
}
