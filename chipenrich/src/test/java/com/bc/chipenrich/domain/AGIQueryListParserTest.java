package com.bc.chipenrich.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.AGI;

import junit.framework.TestCase;

public class AGIQueryListParserTest extends TestCase {

   private AGIQueryListParser parser;
   
   @Override
   protected void setUp() throws Exception {
      parser = new AGIQueryListParser();
   }

   public void testParserOneOnEachLine() {
      String text = "AT1G12345\nAT2G12345";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 2);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT1G12345")));
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT2G12345")));
   }
/*
   public void testParserMultipleOnEachLine() {
      String text = "AT1G12345;AT2G12345";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 2);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT1G12345")));
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT2G12345")));
   }

   public void testParserDuplicates() {
      String text = "AT1G12345\nAT1G12345";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 1);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT1G12345")));
   }
   
   public void testParserDiscardMultipleOnEachLine() {
      String text = "AT1G12345;AT2G12345\nAT3G12345 \t1";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.setDiscardMultipleAGIsOnSameLine(true);
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 1);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT3G12345")));
   }
   
   public void testParserDiscardMultipleOnEachLineSpaces() {
      String text = "AT5G17630;AT5G17640 \t 31\t9 \nAT3G12345 \t1";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.setDiscardMultipleAGIsOnSameLine(true);
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 1);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT3G12345")));
   }
   
   public void testParserTrimming() {
      String text = "AT1G12345 \t\nAT2G12345 \t";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 2);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT1G12345")));
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT2G12345")));
   }

   public void testParserMultipleColumns() {
      String text = "AT1G12345 \t32\nAT2G12345 \t99";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      parser.parse(is);
      assertEquals(parser.getAGIs().size(), 2);
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT1G12345")));
      assertTrue(parser.getAGIs().contains(AGI.createAGI("AT2G12345")));
   }
   */
}
