package com.bc.common.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bc.common.util.Assert;
import com.bc.common.util.CSVParser;


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

   private static final String NAME_FILE = "AGI_names.txt";
   public static final AGI UNKNOWN = new AGI("unknown", "unknown");
   public static final AGI MUTANT = new AGI("mutant available gene unknown", "unknown");
   
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
    	  return checkAGIFormat(id, description);
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

   private static AGI checkAGIFormat(String id, String description) {
	   if (id.equalsIgnoreCase("NIL")) {
		   return UNKNOWN;
	   }
	   CSVParser names = new CSVParser(ClassLoader.getSystemResourceAsStream(NAME_FILE), "\t");
	   for (int i = 0; names.moreLines(); i++) {
		   String[] tokens = names.getTokens();
		   if (tokens[0].equalsIgnoreCase(id)) {
			   if (tokens[1].equalsIgnoreCase("MUTANT")) {
				   return MUTANT;
			   }
			   else return new AGI(tokens[1], description);
		   }
	   }
	   throw new IllegalArgumentException("Illegal AGI=" + id);
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
