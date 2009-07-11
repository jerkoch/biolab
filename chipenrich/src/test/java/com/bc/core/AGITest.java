package com.bc.core;

import com.bc.core.AGI;

import junit.framework.TestCase;

public class AGITest extends TestCase {

   public void testCreationString() {
      try {
         AGI.createAGI("at1g12345");
         AGI.createAGI("AT1G12345");

         AGI.createAGI("at1g12345");
         AGI.createAGI("at2g12345");
         AGI.createAGI("at3g12345");
         AGI.createAGI("at4g12345");
         AGI.createAGI("at5g12345");
      } catch (Exception e) {
         fail("Failed to create AGI");
      }
   }

   public void testCreationInt() {
      try {
         AGI.createAGI(1,5);
         AGI.createAGI(2,5);
         AGI.createAGI(3,5);
         AGI.createAGI(4,5);
         AGI.createAGI(5,5);
      } catch (Exception e) {
         fail("Failed to create AGI");
      }
   }

   public void testEquality() {
      AGI agi1 = AGI.createAGI("at1g00001");
      AGI agi2 = AGI.createAGI("AT1G00001");
      AGI agi3 = AGI.createAGI(1,1);
      assertEquals(agi1, agi2);
      assertEquals(agi1, agi3);
   }
}
