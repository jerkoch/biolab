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
public class SingletonChipRunner extends AbstractRunner {

   public SingletonChipRunner(JLabel status, ChipEnrichService ces, File[] queryFiles,
         String baseOutputDir, boolean runGO, boolean runArray, boolean runTFF, boolean runMetabolics) {
      super(status, ces, queryFiles, "singletons", baseOutputDir, 
    		  (PlantChooser.getInstance().getPlant() + "/SINGLETONS.txt"), true,
    		  runGO, runArray, runTFF, runMetabolics);
   }

   /* (non-Javadoc)
    * @see com.bc.chipenrich.ui.runner.AbstractRunner#getName()
    */
   @Override
   protected String getRunnerName() {
      return "Singleton";
   }

}
