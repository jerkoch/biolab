package com.bc.core;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.bc.util.MapToSetUtil;

public class BackgroundChip {

   private Map<Affy, Set<AGI>> affyMap = new TreeMap<Affy, Set<AGI>>();
   private Map<AGI, Set<Affy>> reverseMap = new TreeMap<AGI, Set<Affy>>();
   private boolean changed;
   
   public void add(Affy affy, AGI agi) {
      MapToSetUtil.addToSet(affyMap, affy, agi);
      changed = true;
   }
   
   public Set<AGI> getAGIs() {
      if (changed == true) {
         reverseMap = MapToSetUtil.reverseMap(affyMap);
         changed = false;
      }
      return reverseMap.keySet();
   }
   
   public int getNumAGIs() {
      return getAGIs().size();
   }
}
