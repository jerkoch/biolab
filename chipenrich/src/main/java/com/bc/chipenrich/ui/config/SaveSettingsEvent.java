package com.bc.chipenrich.ui.config;

import java.io.File;
import java.util.EventObject;
import java.util.Properties;

public class SaveSettingsEvent extends EventObject {

   private Properties properties = new Properties();
   private File settingsFile;

   SaveSettingsEvent(Object source) {
      super(source);
   }

   Properties getProperties() {
      return properties;
   }

   protected File getSettingsFile() {
      return settingsFile;
   }
   
   public void saveSetting(String key, int value) {
      saveSetting(key, "" + value);
   }

   public void saveSetting(String key, long value) {
      saveSetting(key, "" + value);
   }
   
   public void saveSetting(String key, double value) {
      saveSetting(key, "" + value);
   }

   public void saveSetting(String key, Object value) {
      saveSetting(key, value.toString());
   }

   public void saveSetting(String key, String value) {
      properties.put(key, value);
   }

   public void saveSetting(String key, boolean b) {
      properties.put(key, b ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
   }
}
