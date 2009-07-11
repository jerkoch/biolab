package com.bc.chipenrich.ui.updatemgr;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.bc.chipenrich.ui.ChipEnrich;
import com.bc.chipenrich.ui.config.LoadSettingsEvent;
import com.bc.chipenrich.ui.config.SaveSettingsEvent;
import com.bc.chipenrich.ui.config.SettingsListener;

public class UpdateManager implements SettingsListener {

   private static final String CFG_LAST_CHECKED = "update.lastchecked";

   private static final String UPDATE_URL = "http://haloshift.dyndns.org/";
   private static final String UPDATE_VERINFO = ".ce";
   private static final String[] UPDATE_FILES = { "ce.jar", "ce.cmd" };
   private static final String UPDATE_EXT = ".CE_UPD";

   private ChipEnrich parent;
   private UpdateChecker updateChecker;
   private Date lastChecked;

   public UpdateManager(ChipEnrich parent) {
      this.parent = parent;
   }

   public boolean isCheckRequired() {
      // only check for updates once every 24 hours
      Date curTime = Calendar.getInstance().getTime();
      long diff = curTime.getTime() - lastChecked.getTime();
      int days = (int) (diff / (1000 * 60 * 60 * 24));
      return (days >= 1);
   }

   public void check(boolean silent) {
// skip check for now
//      if (updateChecker == null || !updateChecker.isAlive()) {
//         updateChecker = new UpdateChecker(silent);
//         updateChecker.start();
//      }
   }

   private class UpdateChecker extends Thread {
      private boolean silent;

      public UpdateChecker(boolean silent) {
         this.silent = silent;
      }

      public void run() {
         try {
            // create the connection the update url
            URL url = new URL(UPDATE_URL + UPDATE_VERINFO);
            URLConnection uc = url.openConnection();

            // create the properties file to extract releas information
            Properties props = new Properties();
            InputStream is = uc.getInputStream();
            props.load((InputStream) is);
            is.close();

            // compare current version with release
            String version = parent.getVersion();
            String releaseVer = props.getProperty("version");
            if (version != null && releaseVer != null
                  && version.compareTo(releaseVer) < 0) {
               if (showDownloadUpdates(releaseVer)) {
                  downloadUpdates();
                  showRestart();
               }
            } else if (!silent) {
               showNoUpdates();
            }

            lastChecked = Calendar.getInstance().getTime();
         } catch (Exception e) {
            if (!silent) {
               showErrorChecking();
            }
         }

      }
   }

   private void downloadUpdates() throws Exception {

      for (int i = 0; i < UPDATE_FILES.length; i++) {
         URL url = new URL(UPDATE_URL + UPDATE_FILES[i]);
         final URLConnection uc = url.openConnection();
         final byte[] b = new byte[1];

         final DataInputStream dis = new DataInputStream(uc.getInputStream());
         final FileOutputStream fos = new FileOutputStream(UPDATE_FILES[i]
               + UPDATE_EXT);

         final ProgressDialog panel = new ProgressDialog(parent, 0,
               uc.getContentLength(), "Downloading " + UPDATE_FILES[i]);
         panel.setVisible(true);

         Runnable runnable = new Runnable() {
            public void run() {
               try {
                  int i = 0;
                  while (dis.read(b, 0, 1) != -1) {
                     fos.write(b, 0, 1);
                     if (i++ % 10000 == 0) {
                        panel.setProgress(i);
                     }
                  }
                  dis.close();
                  fos.close();
               } catch (Exception ex) {
                  ex.printStackTrace();
               }

               panel.setVisible(false);
            }
         };

         runnable.run();
      }
   }

   private boolean showDownloadUpdates(String ver) {
      int ret = JOptionPane.showConfirmDialog(null,
            "A new version of Log View is available (v" + ver
                  + "). Would you like to download it now?", "Log View",
            JOptionPane.YES_NO_OPTION);
      return (ret == JOptionPane.YES_OPTION);
   }

   private void showRestart() {
      JOptionPane.showMessageDialog(null,
            "You must restart Log View for changes to take effect.", "Log View",
            JOptionPane.INFORMATION_MESSAGE);
   }

   private void showNoUpdates() {
      JOptionPane.showMessageDialog(null, "No updates found!", "Log View",
            JOptionPane.INFORMATION_MESSAGE);
   }

   private void showErrorChecking() {
      JOptionPane.showMessageDialog(null,
            "Error checking for updates.\nUnable to connect to update site.",
            "Log View", JOptionPane.INFORMATION_MESSAGE);
   }

   public void loadSettings(LoadSettingsEvent event) {
      lastChecked = new Date(event.getLong(CFG_LAST_CHECKED));
   }

   public void saveSettings(SaveSettingsEvent event) {
      event.saveSetting(CFG_LAST_CHECKED, lastChecked.getTime());
   }
}
