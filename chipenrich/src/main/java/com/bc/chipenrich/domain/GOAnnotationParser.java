package com.bc.chipenrich.domain;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;
import com.bc.file.CSVParser;
import com.bc.util.MapToSetUtil;

/**
 * The class parser the supplied GO annotation file and creates a gene descriptor map which maps a
 * particular gene descriptor to a unique set of AGI ids. A reverse mapping of AGI ids to unique set
 * of gene descriptors is also provided.
 * 
 * @author Jeremy Koch
 */
public class GOAnnotationParser {

   private Map<AGI, Set<GO>> agiMap = new TreeMap<AGI, Set<GO>>();
   private Set<AGI> filterSet;

   /**
    * Sets the filter set which will discard any parsed AGI ids not found in the supplied set.
    * @param filterSet a set of AGI ids.
    */
   public void setAGIFilterSet(Set<AGI> filterSet) {
      this.filterSet = filterSet;
   }

   /**
    * Parses the input stream into a gene descriptor map.
    * @param is the input stream.
    */
   public void parse(InputStream is) {

      // create the new parser
      CSVParser parser = new CSVParser(is, "\t");
      while (parser.moreLines()) {

         // grab the agi/desc and go ids
         String agiId = parser.nextToken();
         parser.nextToken();
         String goDesc = parser.nextToken();
         String goId = parser.nextToken();

         // check ids are valid
         if (AGI.isAGI(agiId) && GO.isGO(goId)) {

            // create the affy and agi objects
            AGI agi = AGI.createAGI(agiId);
            GO go = GO.createGO(goId, goDesc);

            // check if a filter set is available and verify set contains the
            // agi id
            boolean skip = false;
            if (filterSet != null && !filterSet.contains(agi)) {
               skip = true;
            }

            // if we're not skipping this agi
            if (!skip) {
               // add the association
               MapToSetUtil.addToSet(agiMap, agi, go);
            }
         } else {
         }
      }
   }

   /**
    * Returns the parsed GO gene descriptor map.
    * @return
    */
   public GeneDescriptorMap<GO> getGeneDescriptorMap() {
      return new GeneDescriptorMap<GO>(MapToSetUtil.reverseMap(agiMap));
   }

   /**
    * Returns the AGI map to set of GO gene descriptors.
    * @return
    */
   public Map<AGI, Set<GO>> getAGIMap() {
      return Collections.unmodifiableMap(agiMap);
   }
}
