package com.bc.util;

public class CisMatcherUtil {

   public static int getCisCount(String searchStr, String text) {
      int count = 0;

      if (searchStr.length() == 0) {
         return 0;
      }

      String pattern = createPattern(searchStr);
      for (int i = 0; i < text.length() - searchStr.length() + 1; i++) {
         if (text.substring(i, i + searchStr.length()).matches(pattern)) {
            count++;
         }
      }
      return count;
   }

   private static String createPattern(String searchStr) {
      return searchStr;
   }
}
