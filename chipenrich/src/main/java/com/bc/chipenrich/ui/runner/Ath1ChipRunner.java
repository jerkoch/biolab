/**
 * 
 */
package com.bc.chipenrich.ui.runner;

import java.io.File;

import javax.swing.JLabel;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.locator.WholeChipLocator;

/**
 * @author Jeremy Koch
 */
public class Ath1ChipRunner extends AbstractRunner {

   public Ath1ChipRunner(JLabel status, ChipEnrichService ces, File[] queryFiles,
         String baseOutputDir, boolean runGO, boolean runArray, boolean runTFF, boolean runMetabolics) {
      super(status, ces, queryFiles, "wholechip", baseOutputDir, 
    		  ces.processBackgroundChip(WholeChipLocator.getInstance().getInputStream()), false,
    		  runGO, runArray, runTFF, runMetabolics);
   }
   
   /* (non-Javadoc)
    * @see com.bc.chipenrich.ui.runner.AbstractRunner#getName()
    */
   @Override
   protected String getRunnerName() {
      return "ATH1Chip";
   }
}
