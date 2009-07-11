package com.bc.chipenrich.domain;

import java.io.InputStream;

import com.bc.chipenrich.domain.BackgroundChipParser;

import junit.framework.TestCase;

public class BackgroundChipTest extends TestCase {

   private InputStream is;
   {
      is = getClass().getClassLoader().getResourceAsStream(
            getClass().getPackage().getName().replace('.', '/') + "/testChip.txt");
   }

   public void testParsing() {
      BackgroundChipParser parser = new BackgroundChipParser();
      parser.parse(is);
      
      // make sure we only count agi's once 
      assertEquals(parser.getBackgroundChip().getAGIs().size(), 9);
   }
}
