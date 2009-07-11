package com.bc.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;

public class MapToSetUtilTest extends TestCase {

   public void testAddToSet() {
      Map<String, Set<Integer>> map = new TreeMap<String, Set<Integer>>();
      MapToSetUtil.addToSet(map, "ABC", new Integer(1));
      assertTrue(map.containsKey("ABC"));
      assertEquals(map.size(), 1);
      assertEquals(map.get("ABC").size(), 1);

      MapToSetUtil.addToSet(map, "ABC", new Integer(2));
      assertEquals(map.size(), 1);
      assertEquals(map.get("ABC").size(), 2);

      MapToSetUtil.addToSet(map, "ABC", new Integer(2));
      assertEquals(map.size(), 1);
      assertEquals(map.get("ABC").size(), 2);

      MapToSetUtil.addToSet(map, "XYZ", new Integer(3));
      assertTrue(map.containsKey("XYZ"));
      assertEquals(map.size(), 2);
      assertEquals(map.get("ABC").size(), 2);
      assertEquals(map.get("XYZ").size(), 1);

      MapToSetUtil.addToSet(map, "XYZ", new Integer(1));
      assertEquals(map.size(), 2);
      assertEquals(map.get("ABC").size(), 2);
      assertEquals(map.get("XYZ").size(), 2);
   }
   
   public void testReverseMap() {
      Map<String, Set<Integer>> map = new TreeMap<String, Set<Integer>>();
      assertTrue(MapToSetUtil.reverseMap(map).isEmpty());
      
      MapToSetUtil.addToSet(map, "ABC", new Integer(1));
      assertEquals(MapToSetUtil.reverseMap(map).size(), 1);
      assertTrue(MapToSetUtil.reverseMap(map).containsKey(new Integer(1)));
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(1)).contains("ABC"));
      
      MapToSetUtil.addToSet(map, "ABC", new Integer(2));
      assertEquals(MapToSetUtil.reverseMap(map).size(), 2);
      assertTrue(MapToSetUtil.reverseMap(map).containsKey(new Integer(1)));
      assertTrue(MapToSetUtil.reverseMap(map).containsKey(new Integer(2)));
      assertEquals(MapToSetUtil.reverseMap(map).get(new Integer(1)).size(), 1);
      assertEquals(MapToSetUtil.reverseMap(map).get(new Integer(2)).size(), 1);
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(1)).contains("ABC"));
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(2)).contains("ABC"));

      MapToSetUtil.addToSet(map, "XYZ", new Integer(2));
      assertEquals(MapToSetUtil.reverseMap(map).size(), 2);
      assertTrue(MapToSetUtil.reverseMap(map).containsKey(new Integer(1)));
      assertTrue(MapToSetUtil.reverseMap(map).containsKey(new Integer(2)));
      assertEquals(MapToSetUtil.reverseMap(map).get(new Integer(1)).size(), 1);
      assertEquals(MapToSetUtil.reverseMap(map).get(new Integer(2)).size(), 2);
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(1)).contains("ABC"));
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(2)).contains("ABC"));
      assertTrue(MapToSetUtil.reverseMap(map).get(new Integer(2)).contains("XYZ"));
   }
}
