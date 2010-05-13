package com.bc.chipenrich.ui;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bc.promomer.service.MotifTableMaker;

public class NewPlantAction extends AbstractAction {
	private MainWindow parent;
	private CustomPlantManager plantManager;
	
	public NewPlantAction(MainWindow parent, CustomPlantManager manager) {
		this.parent = parent;
		this.plantManager = manager;
	}
	
	public void actionPerformed(ActionEvent e) {
		CustomPlant newPlant = new CustomPlant();
		
		String plantName = JOptionPane.showInputDialog(parent, "Please enter the name of the new plant");
		if ((plantName == null) || (plantName.equals(""))) {
			return;
		}
		newPlant.setName(plantName);
		
		JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select GO Annotation File(s)");
        fc.setMultiSelectionEnabled(true);
        File[] GOs = null;
        int choice = JOptionPane.showConfirmDialog(parent, "Use GO Annotation Files?",
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	GOs = fc.getSelectedFiles();
        	String GOloc = "";
        	for (int i = 0; i < GOs.length; i++) {
        		GOloc += GOs[i].getAbsolutePath() + ";";
        	}
        	newPlant.setGOs(GOloc);
        }
        
		File wholechip = null;
        fc.setDialogTitle("Select Whole Chip File");
        fc.setMultiSelectionEnabled(false);
        choice = JOptionPane.showConfirmDialog(parent, "Use a whole chip file?",
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	wholechip = fc.getSelectedFile();
        	newPlant.setWholechip(wholechip.getAbsolutePath());
        }
        
        File singleton = null;
        fc.setDialogTitle("Select Singleton Chip File");
        choice = JOptionPane.showConfirmDialog(parent, "Use singleton chip file?",
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	singleton = fc.getSelectedFile();
        	newPlant.setSingleton(singleton.getAbsolutePath());
        }
        
        File metabolic = null;
        fc.setDialogTitle("Select Metabolic File");
        choice = JOptionPane.showConfirmDialog(parent, "Use a metabolic file?", 
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	metabolic = fc.getSelectedFile();
        	newPlant.setMetabolic(metabolic.getAbsolutePath());
        }
        
        File tff = null;
        fc.setDialogTitle("Select Transcription Factor Family File");
        choice = JOptionPane.showConfirmDialog(parent, "Use a transcription factor family file?",
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	tff = fc.getSelectedFile();
        	newPlant.setTFF(tff.getAbsolutePath());
        }
        
        File motif = null;
        fc.setDialogTitle("Select Motif File");
        choice = JOptionPane.showConfirmDialog(parent, "Use a motif file?",
        		"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.CANCEL_OPTION) {
        	return;
        }
        if (choice == JOptionPane.YES_OPTION) {
        	if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        		return;
        	}
        	motif = fc.getSelectedFile();
        	newPlant.setMotif(motif.getAbsolutePath());
        }
        
        File bindingSite = null;
        fc.setDialogTitle("Select Binding Site File");
        //No Motif, GOs, or TFF - Cannot do Analysis - Binding Site File not needed
        if ((motif != null) && (GOs != null) && (tff != null)) {
        	choice = JOptionPane.showConfirmDialog(parent, "Use a binding site file?", 
        			"Message", JOptionPane.YES_NO_CANCEL_OPTION);
        	if (choice == JOptionPane.CANCEL_OPTION) {
        		return;
        	}
        	if (choice == JOptionPane.YES_OPTION) {
        		if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        			return;
        		}
        		bindingSite = fc.getSelectedFile();
        		newPlant.setBindingSite(bindingSite.getAbsolutePath());
        	}
        }

        File fasta = null;
        File AGIMotifTable = null;
        Object options[] = { "Build AGI-Motif Table from FASTA file", "Use preexisting AGI-Motif table" };
        if (motif != null) {	//No need for FASTA if no motif file given
        	choice = JOptionPane.showOptionDialog(parent, 
        			"Do you already have an AGI-Motif table from these files?",
        			"Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
        			options, options[0]);
        	if (choice == JOptionPane.NO_OPTION) {
        		fc.setDialogTitle("Select AGI-Motif Table");
        		if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        			return;
        		}
        		AGIMotifTable = fc.getSelectedFile();
        	}
        	if (choice == JOptionPane.YES_OPTION) {
        		fc.setDialogTitle("Select FASTA File");
        		if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
        			return;
        		}
        		fasta = fc.getSelectedFile();
        		
        		// create and display progress bar
        		ProgressDialog progressDialog = new ProgressDialog();
        		progressDialog.setVisible(true);

        		// setup listener for close
        		progressDialog.addWindowListener(new WindowAdapter() {
        			public void windowClosing(WindowEvent aEvent) {
        			}
        		});
        
        		AGIMotifTable = MotifTableMaker.makeTable(progressDialog.getStatusLabel(), 
        				fasta, motif);
        
        		progressDialog.dispose();
        
        		if (AGIMotifTable == null) {
        			JOptionPane.showMessageDialog(parent, "Error in building table");
        			return;
        		}
        	}
        	newPlant.setTable(AGIMotifTable.getAbsolutePath());
        }
        plantManager.newCustomPlant(newPlant);
	}
	
	private class ProgressDialog extends JDialog {
		private JLabel messageLabel = new JLabel();

		public ProgressDialog() {
			super();
			setTitle("Running...");
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEtchedBorder());
			panel.setLayout(new BorderLayout());

			messageLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
			panel.add(messageLabel, BorderLayout.CENTER);
			getContentPane().add(panel);
			setSize(300, 100);
		}

		public JLabel getStatusLabel() {
			return messageLabel;
		}
	}
}
