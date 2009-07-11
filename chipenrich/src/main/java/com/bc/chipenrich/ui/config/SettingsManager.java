package com.bc.chipenrich.ui.config;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EventListener;
import java.util.Properties;

import javax.swing.event.EventListenerList;

public class SettingsManager {
   private static SettingsManager INSTANCE = new SettingsManager();

   private static final String FILENAME = "chipenrich.properties";
   private static final String DESCRIPTION = "Chip Enrich Configuration Settings";

   private EventListenerList listenerList = new EventListenerList();

   private static final String DEFAULT_PROPERTIES_FILNAME = "com/bc/chipenrich/ui/config/default.properties";
   private Properties defaultProperties = new Properties();
   private Properties userProperties = new Properties();
   protected Properties properties = null;

   private SettingsManager() {
   }

   public static SettingsManager getInstance() {
      return INSTANCE;
   }

   public File getSettingsFile() {
      String folder = System.getProperty("user.home");
      String filesep = System.getProperty("file.separator");
      return new File(folder + filesep + FILENAME);
   }

   public void addSettingsListener(SettingsListener sl) {
      listenerList.add(SettingsListener.class, sl);
   }

   public void removeSettingsListener(SettingsListener sl) {
      listenerList.remove(SettingsListener.class, sl);
   }
   
   private void loadDefaultSettings() {
      InputStream is = null;
      try {
         is = this.getClass().getClassLoader().getResource(
               DEFAULT_PROPERTIES_FILNAME).openStream();
         defaultProperties.load(is);
         is.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception e) {
            }
         }
      }
   }

   private void loadUserSettings() {
      InputStream is = null;
      try {
         String folder = System.getProperty("user.home");
         String filesep = System.getProperty("file.separator");
         is = new FileInputStream(folder + filesep + FILENAME);
         userProperties.load(is);
         is.close();
      } catch (IOException e) {
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception e) {
            }
         }
      }
   }

   public void loadSettings() {
      loadDefaultSettings();
      loadUserSettings();

      Properties loadedProperties = new Properties();
      loadedProperties.putAll(defaultProperties);
      loadedProperties.putAll(userProperties);

      // fire through to all the settings listeners
      EventListener listeners[] = listenerList.getListeners(SettingsListener.class);
      LoadSettingsEvent lse = new LoadSettingsEvent(this, loadedProperties);
      for (int i = 0; i < listeners.length; i++) {
         SettingsListener sl = (SettingsListener) listeners[i];
         sl.loadSettings(lse);
      }
   }

   public void saveSettings() {
      // fire through to all the settings listeners
      EventListener listeners[] = listenerList.getListeners(SettingsListener.class);
      SaveSettingsEvent sse = new SaveSettingsEvent(this);
      for (int i = 0; i < listeners.length; i++) {
         SettingsListener sl = (SettingsListener) listeners[i];
         sl.saveSettings(sse);
      }

      OutputStream os = null;
      try {
         os = new BufferedOutputStream(new FileOutputStream(getSettingsFile()));
         sse.getProperties().store(os, DESCRIPTION);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (IOException e1) {
               e1.printStackTrace();
            }
         }
      }
   }

   public void configure(SettingsListener listener) {
      Properties loadedProperties = new Properties();
      loadedProperties.putAll(defaultProperties);
      loadedProperties.putAll(userProperties);

      LoadSettingsEvent event = new LoadSettingsEvent(this, loadedProperties);
      listener.loadSettings(event);
   }
}
