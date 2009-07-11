package com.bc.chipenrich.domain;

import com.bc.core.GeneDescriptor;
import com.bc.util.DistributionCalculator;

/**
 * The <code>ProbabilityResult</code> class calculates the probability for the supplied gene
 * descriptor.
 * @author Jeremy Koch
 */
public class ProbabilityResult implements Comparable<ProbabilityResult> {

   private GeneDescriptor geneDescriptor;
   private double probability;
   private boolean signficant;

   /**
    * Private contructor used by the
    * {@link #createProbabilityResult(GeneDescriptor, int, int, int, int)} factory method.
    * @param geneDescriptor
    * @param probability
    * @param significant
    */
   private ProbabilityResult(GeneDescriptor geneDescriptor, double probability, boolean significant) {
      this.geneDescriptor = geneDescriptor;
      this.probability = probability;
      this.signficant = significant;
   }

   /**
    * Factory method to create a new <code>ProbabilityResult</code> from the supplied data.
    * @param geneDescriptor
    * @param filteredCount
    * @param filteredSetSize
    * @param backgroundCount
    * @param backgroundSetSize
    * @return
    */
   public static ProbabilityResult createProbabilityResult(GeneDescriptor geneDescriptor,
         int filteredCount, int filteredSetSize, int backgroundCount, int backgroundSetSize) {

      // calc the probability
      double prob = DistributionCalculator.probabilty(backgroundSetSize, backgroundCount,
            filteredSetSize, filteredCount);

      boolean sig = false;
      double perc1 = (double) filteredCount / filteredSetSize;
      double perc2 = (double) backgroundCount / backgroundSetSize;
      if (perc1 > perc2) {
         sig = true;
      }

      return new ProbabilityResult(geneDescriptor, prob, sig);
   }

   /**
    * Returns whether the result is statistically significant.
    * @return
    */
   public boolean isSignificant() {
      return signficant;
   }

   /**
    * Returns the gene descriptor.
    * @return
    */
   public GeneDescriptor getGeneDescriptor() {
      return geneDescriptor;
   }

   /**
    * Returns the probability.
    * @return
    */
   public double getProbability() {
      return probability;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return "[" + geneDescriptor + ",p=" + probability + "]";
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(ProbabilityResult o) {
      return geneDescriptor.getId().compareTo(o.getGeneDescriptor().getId());
   }
}
