package com.bc.chipenrich.domain;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.bc.core.AGI;
import com.bc.core.GeneDescriptorMap;
import com.bc.core.TranscriptionFactorFamily;
import com.bc.file.CSVParser;
import com.bc.util.MapToSetUtil;

public class TranscriptionFactorFamilyParser {

   private Map<AGI, Set<TranscriptionFactorFamily>> agiMap = new TreeMap<AGI, Set<TranscriptionFactorFamily>>();
   private Set<AGI> filterSet;

   public void setAGIFilterSet(Set<AGI> filterSet) {
      this.filterSet = filterSet;
   }

   public void parse(InputStream is) {

      // create the new parser
      CSVParser parser = new CSVParser(is, "\t");
      while (parser.moreLines()) {

         // grab the agi/desc and go ids
         String agiId = parser.nextToken();
         String tffId = parser.nextToken();

         // check ids are valid
         if (AGI.isAGI(agiId)) {

            // create the affy and agi objects
            AGI agi = AGI.createAGI(agiId);
            TranscriptionFactorFamily tff = TranscriptionFactorFamily.createTFF(tffId);

            // check if a filter set is available and verify set contains the
            // agi id
            boolean skip = false;
            if (filterSet != null && !filterSet.contains(agi)) {
               skip = true;
            }

            // if we're not skipping this agi
            if (!skip) {
               // add the association
               MapToSetUtil.addToSet(agiMap, agi, tff);
            }
         } else {
         }
      }
   }

   public GeneDescriptorMap<TranscriptionFactorFamily> getGeneDescriptorMap() {
      return new GeneDescriptorMap<TranscriptionFactorFamily>(MapToSetUtil.reverseMap(agiMap));
   }
}
