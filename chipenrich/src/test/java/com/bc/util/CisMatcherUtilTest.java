package com.bc.util;

import junit.framework.TestCase;

public class CisMatcherUtilTest extends TestCase {

   String text;
   
   public void testGetCisCount() {
      text = "";
      assertEquals(CisMatcherUtil.getCisCount("", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("Z", text), 0);

      text = "A";
      assertEquals(CisMatcherUtil.getCisCount("", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("Z", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("A", text), 1);

      text = "AA";
      assertEquals(CisMatcherUtil.getCisCount("", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("Z", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("A", text), 2);

      text = "AAC";
      assertEquals(CisMatcherUtil.getCisCount("", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("A", text), 2);
      assertEquals(CisMatcherUtil.getCisCount("AA", text), 1);
      assertEquals(CisMatcherUtil.getCisCount("AAC", text), 1);

      text = "AACAAC";
      assertEquals(CisMatcherUtil.getCisCount("", text), 0);
      assertEquals(CisMatcherUtil.getCisCount("A", text), 4);
      assertEquals(CisMatcherUtil.getCisCount("AA", text), 2);
      assertEquals(CisMatcherUtil.getCisCount("AAC", text), 2);
   }
}
