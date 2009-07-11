package com.bc.chipenrich.domain;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;

public class GOAnnotationParserTest extends TestCase {

   private GOAnnotationParser parser;

   private InputStream is;
   {
      is = getClass().getClassLoader().getResourceAsStream(
            getClass().getPackage().getName().replace('.', '/') + "/testGOAnnotations.txt");
   }

   @Override
   protected void setUp() throws Exception {
      parser = new GOAnnotationParser();
      parser.parse(is);
   }

   public void testParsing() {
      assertEquals(parser.getGeneDescriptorMap().getGeneDescriptors().size(), 3);
   }

   public void testParserOneOnEachLine() {
      Set<AGI> filter = new HashSet<AGI>();
      GeneDescriptorMap<GO> gdMap = parser.getGeneDescriptorMap();
      
      assertEquals(gdMap.subsetContainingValues(filter).getGeneDescriptors().size(), 0);

      filter.add(AGI.createAGI("AT1G01010"));
      assertEquals(gdMap.subsetContainingValues(filter).getGeneDescriptors().size(), 3);

      filter.add(AGI.createAGI("AT1G01030"));
      assertEquals(gdMap.subsetContainingValues(filter).getGeneDescriptors().size(), 3);
   }
   
   public void testAssociations() {
      GeneDescriptorMap<GO> gdMap = parser.getGeneDescriptorMap();
      assertEquals(2, gdMap.getAGIs(GO.createGO("GO:0008372")).size());
   }
}
