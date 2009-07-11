package com.bc.chipenrich.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.bc.core.GeneDescriptorMap;
import com.bc.core.TranscriptionFactorFamily;

public class TranscriptionFactorFamilyParserTest extends TestCase {
   
   private TranscriptionFactorFamilyParser parser;
   
   @Override
   protected void setUp() throws Exception {
      parser = new TranscriptionFactorFamilyParser();
   }
   
   public void testParsing() {
      String text = "AT1G12345\tTFF-1\nAT1G54321\tTFF-2";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      GeneDescriptorMap<TranscriptionFactorFamily> gdMap = parser.getGeneDescriptorMap();   

      assertEquals(2, gdMap.getGeneDescriptors().size());
      assertTrue(gdMap.getGeneDescriptors().contains(TranscriptionFactorFamily.createTFF("TFF-1")));
      assertTrue(gdMap.getGeneDescriptors().contains(TranscriptionFactorFamily.createTFF("TFF-2")));
   }
}
