package com.bc.chipenrich.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

      JMenuItem loadGOs = new JMenuItem("Load custom GO Annotations...");
      loadGOs.addActionListener(new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select GO Annotation File(s)...");
            chooser.setMultiSelectionEnabled(true);
            chooser.setApproveButtonText("Select");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
               GOAnnotationLocator.getInstance().setExternalFiles(chooser.getSelectedFiles());
            }
         }
      });

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
              chooser.setApproveButtonText("Select");
              if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                 AGIMotifTableLocator.getInstance().setExternalFile(chooser.getSelectedFile());
              }
    	  }
      });
      
      JMenuItem loadMotif = new JMenuItem ("Use custom motif file...");
      loadMotif.addActionListener(new AbstractAction() {
    	  public void actionPerformed(ActionEvent e) {
    		  JFileChooser chooser = new JFileChooser();
    		  chooser.setCurrentDirectory(new java.io.File("."));
    		  chooser.setDialogTitle("Select motif file...");
    		  chooser.setMultiSelectionEnabled(false);
    		  chooser.setApproveButtonText("Select");
    		  if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    			  MotifFileLocator.getInstance().setExternalFile(chooser.getSelectedFile());
    		  }
    	  }
      });
      
      final JCheckBoxMenuItem ATH1Analysis = new JCheckBoxMenuItem("Include ATH1 Analysis");
      
      JCheckBoxMenuItem ATH1Array = new JCheckBoxMenuItem("Include ATH1 Array");
      ATH1Array.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1Array(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1Array(true);
    		  }
    	  }
      });
      ATH1Array.setSelected(false);
      
      final JCheckBoxMenuItem ATH1GO = new JCheckBoxMenuItem("Include ATH1 GO");
      ATH1GO.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1GO(false);
    			  ATH1Analysis.setSelected(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1GO(true);
    		  }
    	  }
      });
      ATH1GO.setSelected(false);
      
      JCheckBoxMenuItem ATH1Metabolic = new JCheckBoxMenuItem("Include ATH1 Metabolic");
      ATH1Metabolic.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1Metabolic(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1Metabolic(true);
    		  }
    	  }
      });
      ATH1Metabolic.setSelected(false);
      
      JCheckBoxMenuItem ATH1TFF = new JCheckBoxMenuItem("Include ATH1 TFF");
      ATH1TFF.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1TFF(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1TFF(true);
    		  }
    	  }
      });
      ATH1TFF.setSelected(false);
      
      final JCheckBoxMenuItem ATH1Motif = new JCheckBoxMenuItem("Include ATH1 Motif");
      ATH1Motif.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1Motif(false);
    			  ATH1Analysis.setSelected(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1Motif(true);
    		  }
    	  }
      });
      ATH1Motif.setSelected(false);
      
      ATH1Analysis.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setATH1Analysis(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setATH1Analysis(true);
    			  ATH1Motif.setSelected(true);
    			  ATH1GO.setSelected(true);
    		  }
    	  }
      });
      ATH1Analysis.setSelected(false);
      
      final JCheckBoxMenuItem SingletonAnalysis = new JCheckBoxMenuItem("Include Singleton Analysis");
      
      JCheckBoxMenuItem SingletonArray = new JCheckBoxMenuItem("Include Singleton Array");
      SingletonArray.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonArray(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonArray(true);
    		  }
    	  }
      });
      SingletonArray.setSelected(true);
      
      final JCheckBoxMenuItem SingletonGO = new JCheckBoxMenuItem("Include Singleton GO");
      SingletonGO.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonGO(false);
    			  SingletonAnalysis.setSelected(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonGO(true);
    		  }
    	  }
      });
      SingletonGO.setSelected(true);
      
      JCheckBoxMenuItem SingletonMetabolic = new JCheckBoxMenuItem("Include Singleton Metabolic");
      SingletonMetabolic.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonMetabolic(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonMetabolic(true);
    		  }
    	  }
      });
      SingletonMetabolic.setSelected(true);
      
      JCheckBoxMenuItem SingletonTFF = new JCheckBoxMenuItem("Include Singleton TFF");
      SingletonTFF.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonTFF(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonTFF(true);
    		  }
    	  }
      });
      SingletonTFF.setSelected(true);
      
      final JCheckBoxMenuItem SingletonMotif = new JCheckBoxMenuItem("Include Singleton Motif");
      SingletonMotif.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonMotif(false);
    			  SingletonAnalysis.setSelected(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonMotif(true);
    		  }
    	  }
      });
      SingletonMotif.setSelected(true);
      
      SingletonAnalysis.addItemListener(new ItemListener() {
    	  public void itemStateChanged(ItemEvent e) {
    		  if (e.getStateChange() == ItemEvent.DESELECTED) {
    			  RunnerChooser.getInstance().setSingletonAnalysis(false);
    		  }
    		  if (e.getStateChange() == ItemEvent.SELECTED) {
    			  RunnerChooser.getInstance().setSingletonAnalysis(true);
    			  SingletonMotif.setSelected(true);
    			  SingletonGO.setSelected(true);
    		  }
    	  }
      });
      SingletonAnalysis.setSelected(true);
      
/////////////////////////////////////////////

      fileMenu.add(start);
      
      fileMenu.addSeparator();
      
      fileMenu.add(loadGOs);
      fileMenu.add(makeTable);
      fileMenu.add(loadTable);
      fileMenu.add(loadMotif);
      
      fileMenu.addSeparator();
      
      fileMenu.add(ATH1Array);
      fileMenu.add(ATH1GO);
      fileMenu.add(ATH1Metabolic);
      fileMenu.add(ATH1TFF);
      fileMenu.add(ATH1Motif);
      fileMenu.add(ATH1Analysis);
      
      fileMenu.add(SingletonArray);
      fileMenu.add(SingletonGO);
      fileMenu.add(SingletonMetabolic);
      fileMenu.add(SingletonTFF);
      fileMenu.add(SingletonMotif);
      fileMenu.add(SingletonAnalysis);

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
