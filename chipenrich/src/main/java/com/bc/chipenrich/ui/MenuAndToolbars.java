package com.bc.chipenrich.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.bc.chipenrich.ui.locator.AGIMotifTableLocator;

public class MenuAndToolbars {

   ChipEnrich parent;
   JMenuBar menuBar;
   JToolBar toolbar;

   JToggleButton viewSplit;
   JToggleButton viewWordWrap;
   JToggleButton logPause;
   JButton logClear;

   JMenu fileMenu;
   JMenu helpMenu;

   Action orientationAction;
   Action wordWrapAction;
   Action logPauseAction;
   Action logClearAction;

   JMenuItem findNextMark;
   JCheckBoxMenuItem orientation;
   JCheckBoxMenuItem detailsWrap;
   JCheckBoxMenuItem pause;
   JMenuItem clear;

   public MenuAndToolbars(ChipEnrich parent) {
      this.parent = parent;
      toolbar = new JToolBar(SwingConstants.HORIZONTAL);
      menuBar = new JMenuBar();

      fileMenu = new JMenu("File");
      helpMenu = new JMenu("Help");

      createActions();
      createMenuBar();
      createToolbar();
   }

   private void createActions() {
   }

   private void createToolbar() {
   }

   private void createMenuBar() {

      JMenuItem start = new JMenuItem("Start...");
      start.addActionListener(new StartAction(parent));

      JMenuItem about = new JMenuItem("About Chip Enrich");
      about.addActionListener(new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            new AboutWindow(parent).setVisible(true);
         }
      });

      JMenuItem revisionHistory = new JMenuItem("Revision History");
      revisionHistory.addActionListener(new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            new RevisionHistoryWindow(parent).setVisible(true);
         }
      });

      JMenuItem checkForUpdates = new JMenuItem("Check for updates...");
      checkForUpdates.addActionListener(new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            parent.getUpdateManager().check(false);
         }
      });
      
      JMenuItem makeTable = new JMenuItem("Create a custom AGI-Motif Table...");
      makeTable.addActionListener(new CustomTableMaker(parent));
      
      JMenuItem loadTable = new JMenuItem("Use custom AGI-Motif Table...");
      loadTable.addActionListener(new AbstractAction() {
    	  public void actionPerformed(ActionEvent e) {
              JFileChooser chooser = new JFileChooser();
              chooser.setCurrentDirectory(new java.io.File("."));
              chooser.setDialogTitle("Select AGI-Motif Table...");
              chooser.setMultiSelectionEnabled(false);
              if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                 AGIMotifTableLocator.getInstance().setExternalFile(chooser.getSelectedFile());
              }
    	  }
      });
      
      fileMenu.add(start);
      
      fileMenu.addSeparator();
      
      //	fileMenu.add(loadGOs);
      fileMenu.add(makeTable);
      fileMenu.add(loadTable);
      //	fileMenu.add(loadMotif);

      helpMenu.add(revisionHistory);
      helpMenu.addSeparator();
      helpMenu.add(checkForUpdates);
      helpMenu.addSeparator();
      helpMenu.add(about);

      menuBar.add(fileMenu);
      menuBar.add(helpMenu);
   }

   public JToolBar getToolbar() {
      return toolbar;
   }

   public JMenuBar getMenuBar() {
      return menuBar;
   }

   public JMenu getFileMenu() {
      return fileMenu;
   }
}
