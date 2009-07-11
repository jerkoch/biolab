package com.bc.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class CSVParserTest extends TestCase {

   public void testEmptyLines() {
      String text = "A\nB\nC\n\n\n";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      CSVParser parser = new CSVParser(is, "");
      parser.moreLines();
      assertEquals(parser.nextToken(), "A");
      parser.moreLines();
      assertEquals(parser.nextToken(), "B");
      parser.moreLines();
      assertEquals(parser.nextToken(), "C");
      parser.moreLines();
      assertEquals(parser.nextToken(), "");
   }
   
   public void testSeparator() {
      String text = "A,Z\nB,Y\nC\n\n\n";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      CSVParser parser = new CSVParser(is, ",");
      parser.moreLines();
      assertEquals(parser.nextToken(), "A");
      assertEquals(parser.nextToken(), "Z");
      parser.moreLines();
      assertEquals(parser.nextToken(), "B");
      assertEquals(parser.nextToken(), "Y");
      parser.moreLines();
      assertEquals(parser.nextToken(), "C");
      assertEquals(parser.nextToken(), "");
      parser.moreLines();
      assertEquals(parser.nextToken(), "");
   }
}
