package com.bc.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bc.util.Assert;

/**
 * A concrete implementation of the GO gene descriptor.
 * 
 * @author Jeremy Koch
 */
public class GO extends GeneDescriptor implements Comparable<GO> {
   private static final String REGEX = "(GO|go):[0-9]{7,7}";

   private static final String PREFIX = "GO:";

   private static final NumberFormat FORMAT = new DecimalFormat("0000000");

   private GO(String id, String desc) {
      super(id, desc);
   }

   public static GO createGO(int id) {
      if (id <= 0 || id > 9999999) {
         throw new IllegalArgumentException("Illegal 7-digit identifier=" + id);
      }
      return new GO(PREFIX + FORMAT.format(id), null);
   }

   public static GO createGO(String id, String description) {
      // ensure non-null parameter
      Assert.notNull(id);

      // ensure go is in proper format
      if (!id.matches(REGEX)) {
         throw new IllegalArgumentException("Illegal GO=" + id);
      }

      // create the go
      return new GO(id.toUpperCase(), description);
   }

   public static GO createGO(String id) {
      return createGO(id, null);
   }

   public static boolean isGO(String id) {
      return id.matches(REGEX);
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(GO go) {
      return id.compareTo(go.getId());
   }
}
