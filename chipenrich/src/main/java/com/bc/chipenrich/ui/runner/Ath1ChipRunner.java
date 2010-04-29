/**
 * 
 */
package com.bc.chipenrich.ui.runner;

import java.io.File;

import javax.swing.JLabel;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.ui.chooser.PlantChooser;

/**
 * @author Jeremy Koch
 */
public class Ath1ChipRunner extends AbstractRunner {

   public Ath1ChipRunner(JLabel status, ChipEnrichService ces, File[] queryFiles,
         String baseOutputDir, boolean runGO, boolean runArray, boolean runTFF, boolean runMetabolics) {
      super(status, ces, queryFiles, "ath1chip", baseOutputDir, 
    		  (PlantChooser.getInstance().getPlant() + "/ATH1Chip.txt"), false,
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
