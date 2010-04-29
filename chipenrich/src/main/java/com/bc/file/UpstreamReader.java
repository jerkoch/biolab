package com.bc.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.bc.core.AGI;
import com.bc.core.AGIUpstream;

public class UpstreamReader {

   private BufferedReader reader;

   public UpstreamReader(InputStream is) {
      reader = new BufferedReader(new InputStreamReader(is));
      try {
         readUntil('>');
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public AGIUpstream nextAGIUpstream() {
      try {
         String line = reader.readLine();
         if (line == null)	//end of file
        	 return null;
         String[] lines = line.split(" ", 2);
         String agiId = lines[0];
         String upstream = readUntil('>');
         try {
        	 AGI newId = AGI.createAGI(agiId);
        	 return new AGIUpstream(newId, upstream);
         }
         catch(IllegalArgumentException e) {
        	 //Non accepted AGI ID, skip and try next one
        	 return nextAGIUpstream();
         }
      	}
      	catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   // Read from input up to stopChar.  Only includes letter characters.
   // Does not include stopChar in returned string
   private String readUntil(char stopChar) throws IOException {
      int ch;
      StringBuffer sb = new StringBuffer();
      while ((ch = reader.read()) != -1) {
         if (ch != stopChar && Character.isLetter(ch)) {
            sb.append((char) ch);
         } else if (ch == stopChar) {
            break;
         }
      }
      return sb.toString();
   }
}
