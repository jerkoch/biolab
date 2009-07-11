package com.bc.core;

import junit.framework.TestCase;

public class AffyTest extends TestCase {

   public void testCreationString() {
      try {
         Affy.createAffy("123456_at");
         Affy.createAffy("123456_AT");
         Affy.createAffy("123456_s_at");
         Affy.createAffy("123456_S_at");
      } catch (Exception e) {
         fail("Failed to create Affy");
      }
   }

   public void testCreationInt() {
      try {
         Affy.createAffy(1);
         Affy.createAffy(999999);
      } catch (Exception e) {
         fail("Failed to create Affy");
      }
   }

   public void testCreationFailedInt() {
      try {
         Affy.createAffy(0);
         fail("Illegal argument should have been thrown");
      } catch (IllegalArgumentException e) {
      }

      try {
         Affy.createAffy(1000000);
         fail("Illegal argument should have been thrown");
      } catch (IllegalArgumentException e) {
      }
   }

   public void testEquality() {
      Affy Affy1 = Affy.createAffy("000001_at");
      Affy Affy2 = Affy.createAffy("000001_AT");
      Affy Affy3 = Affy.createAffy(1);
      assertEquals(Affy1, Affy2);
      assertEquals(Affy1, Affy3);
   }
}
