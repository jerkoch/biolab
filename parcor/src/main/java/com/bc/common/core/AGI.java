package com.bc.common.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bc.common.util.Assert;


/**
 * Arabidopsis Genome Initiative
 * 
 * @author Jeremy Koch
 */
public class AGI implements Comparable<AGI> {

   private static final String REGEX = "(A|a)(T|t)([1-5]|M|m|C|c)(G|g)[0-9]{5,5}";

   private static final String PREFIX = "AT";
   private static final String GENOME = "G";

   private static final NumberFormat FORMAT = new DecimalFormat("00000");

   public static final AGI UNKNOWN = new AGI("unknown", "unknown");
   
   private String id;
   private String desc;

   private AGI(String id, String desc) {
      this.id = id;
      this.desc = desc;
   }

   public String getId() {
      return id;
   }

   public String getDescription() {
      return desc;
   }

   public void setDescription(String desc) {
      this.desc = desc;
   }
   
   public static AGI createAGI(int chromosome, int id) {
      if (chromosome <= 0 || chromosome > 5) {
         throw new IllegalArgumentException("Illegal chromosome identifier=" + chromosome);
      }
      if (id <= 0 || id > 99999) {
         throw new IllegalArgumentException("Illegal 5-digit identifier=" + id);
      }
      return new AGI(PREFIX + chromosome + GENOME + FORMAT.format(id), null);
   }

   public static AGI createAGI(String id, String description) {
      // ensure non-null parameter
      Assert.notNull(id);

      // ensure agi is in proper format
      if (!id.matches(REGEX)) {
         throw new IllegalArgumentException("Illegal AGI=" + id);
      }

      // create the agi
      return new AGI(id.toUpperCase(), description);
   }

   public static AGI createAGI(String id) {
      return createAGI(id, null);
   }

   public static boolean isAGI(String id) {
      return id.matches(REGEX);
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj instanceof AGI) {
         return id.equals(((AGI) obj).getId());
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
   public int compareTo(AGI agi) {
      return id.compareTo(agi.getId());
   }
}
