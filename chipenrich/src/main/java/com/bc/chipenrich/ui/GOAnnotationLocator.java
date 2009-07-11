/**
 * 
 */
package com.bc.chipenrich.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.bc.util.ResourceUtil;

/**
 * @author Jeremy Koch
 */
public class GOAnnotationLocator {

   private static GOAnnotationLocator INSTANCE = new GOAnnotationLocator();
   
   private File[] externalFiles;

   public static GOAnnotationLocator getInstance() {
      return INSTANCE;
   }
     
   public InputStream[] getInputStreams() {
      if (externalFiles == null) {
         return ResourceUtil.getInputStreams(getClass().getClassLoader(), new String[] {
            "GOAnnotations1.txt", "GOAnnotations2.txt", "GOAnnotations3.txt", "GOAnnotations4.txt",
         "GOAnnotations5.txt" });
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
