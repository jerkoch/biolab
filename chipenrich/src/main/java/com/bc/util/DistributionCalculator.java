package com.bc.util;

import org.apache.commons.math.distribution.DistributionFactory;
import org.apache.commons.math.distribution.HypergeometricDistribution;

public class DistributionCalculator {

   private static DistributionFactory factory = DistributionFactory.newInstance();

   public static double probabilty(int backgroundChipSize, int countOnBackground,
         int filterSetSize, int countFiltered) {

      // create the distribution
      HypergeometricDistribution dist = factory.createHypergeometricDistribution(
            backgroundChipSize, countOnBackground, filterSetSize);

      // return the probability
      return dist.probability(countFiltered);
   }
}
