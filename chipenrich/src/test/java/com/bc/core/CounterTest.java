package com.bc.core;

import com.bc.core.Counter;

import junit.framework.TestCase;

public class CounterTest extends TestCase {
   
   private Counter counter;
   Object obj = new Object();
   Object obj2 = new Object();

   @Override
   protected void setUp() throws Exception {
      counter = new Counter();
   }
   
   public void testAdding() {
      assertEquals(counter.getCount(obj), 0);
      counter.add(obj);
      assertEquals(counter.getCount(obj), 1);
      counter.add(obj);
      assertEquals(counter.getCount(obj), 2);
      counter.add(obj2);
      assertEquals(counter.getCount(obj), 2);
      assertEquals(counter.getCount(obj2), 1);
   }
   
   public void testRemoving() {
      counter.remove(obj);
      assertEquals(counter.getCount(obj), 0);
      counter.add(obj);
      counter.add(obj);
      counter.remove(obj);
      assertEquals(counter.getCount(obj), 1);
   }
}
