package com.bc.core;

import junit.framework.TestCase;

public class TranscriptionFactorFamilyTest extends TestCase {

   public void testCreationString() {
      try {
         TranscriptionFactorFamily.createTFF("TFF-1");
         TranscriptionFactorFamily.createTFF("TFF-2");
      } catch (Exception e) {
         fail("Failed to create TranscriptionFactorFamily");
      }
   }

   public void testEquality() {
      TranscriptionFactorFamily TranscriptionFactorFamily1 = TranscriptionFactorFamily.createTFF("TFF-1");
      TranscriptionFactorFamily TranscriptionFactorFamily2 = TranscriptionFactorFamily.createTFF("TFF-1");
      assertEquals(TranscriptionFactorFamily1, TranscriptionFactorFamily2);
   }
}
