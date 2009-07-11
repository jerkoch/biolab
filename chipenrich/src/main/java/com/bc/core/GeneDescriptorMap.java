package com.bc.core;

import java.util.Map;
import java.util.Set;

import com.bc.util.MapToSetUtil;

public class GeneDescriptorMap<T extends GeneDescriptor> {

   private Map<T, Set<AGI>> map;
   
   public GeneDescriptorMap(Map<T, Set<AGI>> map) {
      this.map = map;
   }
      
   public GeneDescriptorMap<T> subsetContainingValues(Set<AGI> agiSet) {
      Map<AGI, Set<T>> subMap = MapToSetUtil.reverseMap(map);
      subMap.keySet().retainAll(agiSet);
      return new GeneDescriptorMap<T>(MapToSetUtil.reverseMap(subMap));
   }
   
   public Set<T> getGeneDescriptors() {
      return map.keySet();
   }
   
   public Set<AGI> getAGIs(GeneDescriptor geneDescriptor) {
      return map.get(geneDescriptor);
   }
}
