package com.bc.chipenrich.domain;

import java.io.InputStream;

import com.bc.core.AGI;
import com.bc.core.Affy;
import com.bc.core.BackgroundChip;
import com.bc.file.CSVParser;

/**
 * The <code>BackgroundChipParser</code> parses the supplied <code>InputStream</code> into a
 * <code>BackgroundChip</code> object representing the set of AGI ids.
 * 
 * @author Jeremy Koch
 */
public class BackgroundChipParser {

   private BackgroundChip backgroundChip = new BackgroundChip();

   /**
    * Parses the AGI ids from the supplied <code>InputStream</code>.
    * @param is the input stream to parse.
    */
   public void parse(InputStream is) {

      // create the new parser
      CSVParser parser = new CSVParser(is, "\t");
      while (parser.moreLines()) {

         // grab the affy and AGI ids
         String affyId = parser.nextToken();
         String agiId = parser.nextToken();

         // check ids are valid
         if (Affy.isAffy(affyId) && AGI.isAGI(agiId)) {

            // create the affy and agi objects
            Affy affy = Affy.createAffy(affyId);
            AGI agi = AGI.createAGI(agiId);

            // add the association
            backgroundChip.add(affy, agi);
         } else {
         }
      }
   }

   /**
    * Returns the <code>BackgroundChip</code> object.
    * @return the background chip.
    */
   public BackgroundChip getBackgroundChip() {
      return backgroundChip;
   }
}
