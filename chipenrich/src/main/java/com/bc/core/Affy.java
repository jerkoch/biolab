package com.bc.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bc.util.Assert;

/**
 * Arabidopsis Genome Initiative
 * 
 * @author Jeremy Koch
 */
public class Affy implements Comparable<Affy> {

   private static final String REGEX = "[0-9]{6,6}_((s|S|x|S)_)?(a|A)(t|T)";

   private static final String POSTFIX = "_at";

   private static final NumberFormat FORMAT = new DecimalFormat("000000");

   private String id;

   private Affy(String id) {
      this.id = id;
   }

   public String getId() {
      return id;
   }

   public static Affy createAffy(int id) {
      if (id <= 0 || id > 999999) {
         throw new IllegalArgumentException("Illegal 6-digit identifier=" + id);
      }
      return new Affy(FORMAT.format(id) + POSTFIX);
   }

   public static Affy createAffy(String id) {
      // ensure non-null parameter
      Assert.notNull(id);

      /* FIXME: Affy ID's for both arabidopsis and soybean
      // ensure Affy is in proper format
      if (!id.matches(REGEX)) {
         throw new IllegalArgumentException("Illegal Affy=" + id);
      }
      */

      // create the Affy
      return new Affy(id.toLowerCase());
   }

   public static boolean isAffy(String id) {
	  /*	
      return id.matches(REGEX);
      */
	   return true;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj instanceof Affy) {
         return id.equals(((Affy) obj).getId());
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return id.hashCode();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return id;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Affy affy) {
      return id.compareTo(affy.getId());
   }
}
