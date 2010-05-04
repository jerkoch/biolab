/**
 * 
 */
package com.bc.chipenrich.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.AbstractAction;

import com.bc.chipenrich.ui.chooser.PlantChooser;
import com.bc.util.ResourceUtil;

/**
 * @author Jeremy Koch
 */
public class GOAnnotationLocator extends AbstractAction {
   private static GOAnnotationLocator INSTANCE = new GOAnnotationLocator();
   private JLabel location;
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
   
   public void setLabel (JLabel label) {
	   this.location = label;
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
      String label = "";
      for (File file : files) {
    	  label += file.getAbsolutePath();
    	  label += "; ";
      }
      location.setText(label);
   }
   
   public void actionPerformed(ActionEvent e) {
	   JFileChooser chooser = new JFileChooser();
	   chooser.setCurrentDirectory(new java.io.File("."));
	   chooser.setDialogTitle("Select GO Annotation File(s)...");
	   chooser.setMultiSelectionEnabled(true);
	   chooser.setApproveButtonText("Select");
	   if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		   this.setExternalFiles(chooser.getSelectedFiles());
	   }
   }
}
