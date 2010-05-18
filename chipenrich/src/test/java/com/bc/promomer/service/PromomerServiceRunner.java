package com.bc.promomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

import junit.framework.TestCase;

public class PromomerServiceRunner extends TestCase {

   public void testFile() {
      try {
    	  /*
    	  FileInputStream input = new FileInputStream("C:/Users/Corey/Desktop/GMChipEnrich/Soybean_GO_Annotation_AgriGO.txt");
    	  BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    	  FileOutputStream output = new FileOutputStream("C:/Users/Corey/Desktop/Soybean_GO_Annotation_AgriGO.txt");
    	  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
    	  String nextLine = reader.readLine();
    	  while (nextLine != null) {
    		  String[] nextLineSplit = nextLine.split("\t", 2);
    		  writer.write(nextLineSplit[0]);
    		  writer.write('\t');
    		  writer.write("xxx");
    		  writer.write('\t');
    		  if (nextLineSplit.length >= 2) {
    			  writer.write(nextLineSplit[1]);
    		  }
    		  writer.write('\n');
    		  writer.flush();
    		  nextLine = reader.readLine();
    	  }
    	  writer.close();
    	  */
      } catch (Exception e) {
    	  e.printStackTrace();
    	  System.out.println("ERROR");
      }
   }
}