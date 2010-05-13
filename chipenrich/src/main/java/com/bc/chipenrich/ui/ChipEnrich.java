package com.bc.chipenrich.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.bc.chipenrich.ui.config.LoadSettingsEvent;
import com.bc.chipenrich.ui.config.SaveSettingsEvent;
import com.bc.chipenrich.ui.config.SettingsListener;
import com.bc.chipenrich.ui.config.SettingsManager;
import com.bc.chipenrich.ui.updatemgr.UpdateManager;

public class ChipEnrich extends JFrame implements SettingsListener {

   public static final String CFG_WINDOWSIZE_X = "view.window.size.x";
   public static final String CFG_WINDOWSIZE_Y = "view.window.size.y";

   private SettingsManager sman = SettingsManager.getInstance();
   private UpdateManager updateManager;
   private MenuAndToolbars menus;
   private StatusBar statusBar;

   public ChipEnrich() {
      super();

      // create the tile
      setTitle("Chip Enrich");

      // create the menu
      menus = new MenuAndToolbars(this);

      // set the main menu bar
      setJMenuBar(getMenus().getMenuBar());

      statusBar = new StatusBar(this);

      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(getMenus().getToolbar(), BorderLayout.NORTH);
      this.getContentPane().add(new MainWindow(), BorderLayout.CENTER);
      this.getContentPane().add(statusBar, BorderLayout.SOUTH);

      // setup listener for close
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent aEvent) {
            SettingsManager.getInstance().saveSettings();
            System.exit(0);
         }
      });

      // create the update checker
      updateManager = new UpdateManager(this);

      // load settings
      sman.addSettingsListener(this);
      sman.addSettingsListener(updateManager);
      sman.loadSettings();

      // check for updates
      if (updateManager.isCheckRequired()) {
         updateManager.check(true);
      }

      setVisible(true);
   }

   public void loadSettings(LoadSettingsEvent event) {
      // set the size
      setSize(event.getInt(CFG_WINDOWSIZE_X), event.getInt(CFG_WINDOWSIZE_Y));
   }

   public void saveSettings(SaveSettingsEvent event) {
      event.saveSetting(CFG_WINDOWSIZE_X, getWidth());
      event.saveSetting(CFG_WINDOWSIZE_Y, getHeight());
   }

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
      }

      new ChipEnrich();

   }

   public String getVersion() {
      Package p = getClass().getPackage();
      return p.getImplementationVersion();
   }

   public MenuAndToolbars getMenus() {
      return menus;
   }

   public StatusBar getStatusBar() {
      return statusBar;
   }
   
   public UpdateManager getUpdateManager() {
      return updateManager;
   }
}
