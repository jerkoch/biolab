package com.bc.core;

import com.bc.util.Assert;

/**
 * Arabidopsis Genome Initiative
 * 
 * @author Jeremy Koch
 */
public class TranscriptionFactorFamily extends GeneDescriptor implements
      Comparable<TranscriptionFactorFamily> {

   private TranscriptionFactorFamily(String id, String desc) {
      super(id, desc);
   }

   public static TranscriptionFactorFamily createTFF(String id) {
      // ensure non-null parameter
      Assert.notNull(id);

      // create the TFF
      return new TranscriptionFactorFamily(id.toUpperCase(), null);
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(TranscriptionFactorFamily tff) {
      return id.compareTo(tff.getId());
   }
}
