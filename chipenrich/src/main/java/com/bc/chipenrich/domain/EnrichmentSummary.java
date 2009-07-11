package com.bc.chipenrich.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.bc.core.GeneDescriptor;

/**
 * This <code>EnrichmentSummary</code> class is the container for the calculated probabilities and
 * aides in outputing the results to file.
 * 
 * @author Jeremy Koch
 */
public class EnrichmentSummary {

   private Map<SummaryKey, ProbabilityResult> map = new HashMap<SummaryKey, ProbabilityResult>();
   private Set<GeneDescriptor> geneDescriptorSet = new TreeSet<GeneDescriptor>();
   private Set<String> nameSet = new TreeSet<String>();

   /**
    * Adds a new map entry with the supplied query list name and probability result for a particular
    * gene descriptor. The table key is the combined query list name and gene descriptor from the
    * probability result.
    * @param name the query list name.
    * @param pr the probability result.
    */
   public void add(String name, ProbabilityResult pr) {
      map.put(new SummaryKey(name, pr.getGeneDescriptor()), pr);
      nameSet.add(name);
      geneDescriptorSet.add(pr.getGeneDescriptor());
   }

   /**
    * Returns the complete set of query list names which is used for the column headings in the
    * outputted table.
    * @return
    */
   public Set<String> getSetNames() {
      return nameSet;
   }

   /**
    * Returns the complete set of gene descriptors which is used for each row in the outputted
    * table.
    * @return
    */
   public Set<GeneDescriptor> getGeneDescriptors() {
      return geneDescriptorSet;
   }

   /**
    * Returns the probability result for the supplied query list name and gene descriptor.
    * @param setName the query list name.
    * @param geneDescriptor the gene descriptor.
    * @return
    */
   public ProbabilityResult getProbabilityResult(String setName, GeneDescriptor geneDescriptor) {
      return map.get(new SummaryKey(setName, geneDescriptor));
   }

   /**
    * Determines is the supplied gene descriptor is statistically significant in any of the agi
    * query list columns.
    * @param geneDescriptor
    * @return
    */
   public boolean isSignificant(GeneDescriptor geneDescriptor) {
      for (String setName : getSetNames()) {
         ProbabilityResult pr = getProbabilityResult(setName, geneDescriptor);
         if (pr != null && Math.log10(pr.getProbability()) <= -3) {
            return true;
         }
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return map.toString();
   }

   /**
    * Inner class to encapsulate the combined probability result table key of the query list
    * (column) and gene descriptor (row).
    * 
    * @author Jeremy Koch
    */
   public class SummaryKey {
      public String setName;
      public GeneDescriptor geneDescriptor;

      /**
       * Constructs a new <code>SummaryKey</code>.
       * @param setName
       * @param geneDescriptor
       */
      public SummaryKey(String setName, GeneDescriptor geneDescriptor) {
         this.setName = setName;
         this.geneDescriptor = geneDescriptor;
      }

      /*
       * (non-Javadoc)
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object obj) {
         if (!(obj instanceof SummaryKey))
            return false;

         SummaryKey key = (SummaryKey) obj;
         return setName.equals(key.setName) && geneDescriptor.equals(key.geneDescriptor);
      }

      /*
       * (non-Javadoc)
       * @see java.lang.Object#hashCode()
       */
      public int hashCode() {
         return setName.hashCode() + geneDescriptor.hashCode();
      }

      /*
       * (non-Javadoc)
       * @see java.lang.Object#toString()
       */
      public String toString() {
         return setName + "/" + geneDescriptor;
      }
   }
}
