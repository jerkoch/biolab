package com.bc.chipenrich.domain;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.bc.core.AGI;
import com.bc.file.CSVParser;

/**
 * The <code>AGIQueryListParser</code> is responsible for extracting the set (unique) of AGI ids
 * from the supplied <code>InputStream</code>.
 * <p>
 * Multiple AGIs appearing on the same line (separated by ';' or '\t' will be included by default.
 * Use {@link #setDiscardMultipleAGIsOnSameLine(boolean)} to disable this behavior.
 * 
 * @author Jeremy Koch
 */
public class AGIQueryListParser {

   private boolean discardMultipleAGIsOnSameLine;

   public static final String ELEMENT_SEPARATOR = ";\t";
   public static final String ELEMENT_SEPARATOR_IGNORE_SEMICOLON = "\t";

   private Set<AGI> agiSet = new HashSet<AGI>();

   /**
    * Sets whether multiple AGIs appearing on the same line should be completly discarded. The is
    * used when running against the SINGLETONs background chip.
    * @param discardMultipleAGIsOnSameLine true to discard, false otherwise.
    */
   public void setDiscardMultipleAGIsOnSameLine(boolean discardMultipleAGIsOnSameLine) {
      this.discardMultipleAGIsOnSameLine = discardMultipleAGIsOnSameLine;
   }

   /**
    * Parses the AGI ids from the supplied <code>InputStream</code>.
    * @param is the input stream to parse.
    */
   public void parse(InputStream is) {

      // agis may be separated on same line by the defined element separator
      // might need to ignore line containing this separator
      String separator = ELEMENT_SEPARATOR;
      if (discardMultipleAGIsOnSameLine) {
         separator = ELEMENT_SEPARATOR_IGNORE_SEMICOLON;
      }

      // create the new parser
      CSVParser parser = new CSVParser(is, "");
      while (parser.moreLines()) {

         // agis may be separated on same line by the defined element separator
         StringTokenizer st = new StringTokenizer(parser.nextToken(), separator);
         while (st.hasMoreTokens()) {

            // trim and whitespace around the token
            String text = st.nextToken().trim();
            if (AGI.isAGI(text)) {
               agiSet.add(AGI.createAGI(text));
            }
         }
      }
   }

   /**
    * Returns the set of unique AGI ids contained in the parsed query list.
    * @return the AGI ids.
    */
   public Set<AGI> getAGIs() {
      return agiSet;
   }
}
