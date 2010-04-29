/**
 * 
 */
package com.bc.chipenrich.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.bc.util.ResourceUtil;
import com.bc.chipenrich.ui.chooser.PlantChooser;

/**
 * @author Jeremy Koch
 */
public class GOAnnotationLocator {

   private static GOAnnotationLocator INSTANCE = new GOAnnotationLocator();
   
   private File[] externalFiles;
   private String[] arabidopsisGO = new String[] {
           "arabidopsis/GOAnnotations1.txt", "arabidopsis/GOAnnotations2.txt",
           "arabidopsis/GOAnnotations3.txt", "arabidopsis/GOAnnotations4.txt",
           "arabidopsis/GOAnnotations5.txt" };
   private String[] soybeanGO = new String[] {
		   "soybean/GOAnnotations.txt" };

   public static GOAnnotationLocator getInstance() {
      return INSTANCE;
   }
     
   public InputStream[] getInputStreams() {
      if (externalFiles == null) {
    	  //No custom GO - Use default for plant
    	  String plant = PlantChooser.getInstance().getPlant();
    	  if (plant == "arabidopsis") {
    		  return ResourceUtil.getInputStreams(getClass().getClassLoader(), arabidopsisGO);
    	  }
    	  // else plant = soybean
    	  else {
    		  return ResourceUtil.getInputStreams(getClass().getClassLoader(), soybeanGO);
    	  }
      } else {
         try {
            InputStream[] inputStreams = new InputStream[externalFiles.length];
            for (int i = 0; i < externalFiles.length; i++) {
               inputStreams[i] = new FileInputStream(externalFiles[i]);
            }
            return inputStreams;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
         }         
      }
   }
     
   public void setExternalFiles(File[] files) {
      this.externalFiles = files;
   }
}
