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
public class SingletonChipRunner extends AbstractRunner {

   public SingletonChipRunner(JLabel status, ChipEnrichService ces, File[] queryFiles,
         String baseOutputDir) {
      super(status, ces, queryFiles, "singletons", baseOutputDir, "SINGLETONS.txt", true);
   }

   /* (non-Javadoc)
    * @see com.bc.chipenrich.ui.runner.AbstractRunner#getName()
    */
   @Override
   protected String getRunnerName() {
      return "Singleton";
   }

}
