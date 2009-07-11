package com.bc.core;

import junit.framework.TestCase;

public class GOTest extends TestCase {

   public void testCreationString() {
      try {
         GO.createGO("GO:1234567");
         GO.createGO("go:1234567");
      } catch (Exception e) {
         fail("Failed to create GO");
      }
   }

   public void testCreationInt() {
      try {
         GO.createGO(1);
         GO.createGO(9999999);
      } catch (Exception e) {
         fail("Failed to create GO");
      }
   }

   public void testCreationFailedInt() {
      try {
         GO.createGO(0);
         fail("Illegal argument should have been thrown");
      } catch (IllegalArgumentException e) {
      }

      try {
         GO.createGO(10000000);
         fail("Illegal argument should have been thrown");
      } catch (IllegalArgumentException e) {
      }
   }

   public void testEquality() {
      GO GO1 = GO.createGO("go:0000001");
      GO GO2 = GO.createGO("GO:0000001");
      GO GO3 = GO.createGO(1);
      assertEquals(GO1, GO2);
      assertEquals(GO1, GO3);
   }
}
