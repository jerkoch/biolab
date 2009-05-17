package com.bc.common.util;

public abstract class Assert {

   public static void notNull(Object obj) {
      if (obj == null) {
         throw new IllegalArgumentException("[Assert] Argument must not be null");
      }
   }
}
