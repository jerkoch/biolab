package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.core.BackgroundChip;
import com.bc.util.ResourceUtil;
import com.bc.file.AGIMotifReader;

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
	   try {
		   System.out.println("Test Table");
		   PromomerTable service = new PromomerTableImpl();
		   AGIMotifReader tableReader = new AGIMotifReader(new FileInputStream("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/AGI_Motif_Table.txt"));
//		   service.getCisCount(backgroundChip,
//				   new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/test/test_Motif.txt"),
//				   ResourceUtil.getFiles("C:/Documents and Settings/Corey Harada/Desktop/Mac/to_process"),
//				   tableReader, "");
	   } catch (Exception e) {
		   e.printStackTrace();
	   }

   }
}
