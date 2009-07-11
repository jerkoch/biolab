package com.bc.core;

public class AGIUpstream {

   private AGI agi;
   private String upstream;
   
   public AGIUpstream(AGI agi, String upstream) {
      this.agi = agi;
      this.upstream = upstream;
   }

   public AGI getAgi() {
      return agi;
   }

   public String getUpstream() {
      return upstream;
   }
}
