package com.bc.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class MapToSetUtil {

   public static <K, V> Map<V, Set<K>> reverseMap(Map<K, Set<V>> map) {
      Map<V, Set<K>> reverseMap = new TreeMap<V, Set<K>>();
      for (K k : map.keySet()) {
         Set<V> set = map.get(k);
         for (V v : set) {
            addToSet(reverseMap, v, k);
         }
      }
      return reverseMap;
   }

   public static <K, V> void addToSet(Map<K, Set<V>> map, K k, V v) {
      // if the map doesn't contain the reverse association, then add the empty list
      if (!map.containsKey(k)) {
         map.put(k, new TreeSet<V>());
      }
      // add the association
      map.get(k).add(v);
   }
}
