package com.bc.util;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;

public class ResourceUtil {

   public static File[] getFiles(String path) {
      File dir = new File(path);
      FileFilter fileFilter = new FileFilter() {
         public boolean accept(File file) {
            if (file.isFile()) {
               return true;
            }
            return false;
         }
      };
      return dir.listFiles(fileFilter);
   }

   public static InputStream[] getInputStreams(ClassLoader classLoader, String[] resourceName) {
      InputStream[] inputStreams = new InputStream[resourceName.length];
      try {
         for (int i=0; i < resourceName.length; i++) {
            inputStreams[i] = classLoader.getResourceAsStream(resourceName[i]);
         }
      } catch (Exception e) {
      }
      return inputStreams;
   }
   
   public static File[] getFiles(String path, final String filter) {
      File dir = new File(path);
      FileFilter fileFilter = new FileFilter() {
         public boolean accept(File file) {
            if (file.isFile()) {
               return file.getAbsolutePath().contains(filter);
            }
            return false;
         }
      };
      return dir.listFiles(fileFilter);
   }
}