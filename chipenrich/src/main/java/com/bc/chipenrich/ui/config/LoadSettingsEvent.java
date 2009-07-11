package com.bc.chipenrich.ui.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class LoadSettingsEvent extends EventObject {

   Properties properties; 
   
   public LoadSettingsEvent(Object source, Properties properties) {
      super(source);
      this.properties = properties;
   }
   
   public long getLong(String key) {
      try {
         return Long.parseLong(properties.getProperty(key));
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Invalid configuration parameter: " + key);
      }
   }
   
   public int getInt(String key) {
      try {
         return Integer.parseInt(properties.getProperty(key));
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Invalid configuration parameter: " + key);
      }
   }
   
   public String getString(String name) {
      return properties.getProperty(name);
   }
   
   public boolean getBoolean(String key) {
      return Boolean.valueOf(getString(key)).booleanValue();
   }
   
   public Collection getStartingWith(String key) {
      Collection<Object> col = new ArrayList<Object>();
      Iterator iter = properties.entrySet().iterator();
      while (iter.hasNext()) {
         Map.Entry entry = (Map.Entry)iter.next();
         if (((String)entry.getKey()).startsWith(key))
            col.add(entry.getValue());
      }
      return Collections.unmodifiableCollection(col);
   }
}
