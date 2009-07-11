package com.bc.core;

import java.util.HashMap;
import java.util.Map;

public class Counter {
   private Map<Object, Integer> map = new HashMap<Object, Integer>();

   public void add(Object obj) {
      Integer i = map.get(obj);
      if (i == null) {
         i = new Integer(1);
      } else {
         i = new Integer(i.intValue() + 1);
      }
      map.put(obj, i);
   }

   public void remove(Object obj) {
      Integer i = map.get(obj);
      if (i != null) {
         map.put(obj, new Integer(i.intValue() - 1));
      }
   }

   public int getCount(Object obj) {
      Integer i = map.get(obj);
      if (i == null) {
         return 0;
      }
      return i.intValue();
   }
   
   @Override
   public String toString() {
      return map.toString();
   }
}
