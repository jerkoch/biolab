/**
 * 
 */
package com.bc.chipenrich.ui.runner;

import java.io.File;

import javax.swing.JLabel;

import com.bc.chipenrich.service.ChipEnrichService;

/**
 * @author Jeremy Koch
 */
public class Ath1ChipRunner extends AbstractRunner {

   public Ath1ChipRunner(JLabel status, ChipEnrichService ces, File[] queryFiles,
         String baseOutputDir) {
      super(status, ces, queryFiles, "ath1chip", baseOutputDir, "ATH1Chip.txt", false);
   }
   
   /* (non-Javadoc)
    * @see com.bc.chipenrich.ui.runner.AbstractRunner#getName()
    */
   @Override
   protected String getRunnerName() {
      return "ATH1Chip";
   }
}
