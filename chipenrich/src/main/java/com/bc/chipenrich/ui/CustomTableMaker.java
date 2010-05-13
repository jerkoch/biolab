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

public class CustomTableMaker extends AbstractAction {
	
	private ChipEnrich parent;
	
	public CustomTableMaker(ChipEnrich parent) {
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent event) {
		new Thread(new Runnable() {
			public void run() {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Select FASTA File...");
	            chooser.setMultiSelectionEnabled(false);
	            if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
	            	return;
	            }
	            File fastaFile = chooser.getSelectedFile();
	            
                chooser.setDialogTitle("Select motif file...");
                if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                	return;
                }
                File motifFile = chooser.getSelectedFile();
				
		        // create and display the dialog
	            ProgressDialog progressDialog = new ProgressDialog();
	            progressDialog.setVisible(true);

	            // setup listener for close
	            progressDialog.addWindowListener(new WindowAdapter() {
	               public void windowClosing(WindowEvent aEvent) {
	               }
	            });
	            
	            //Make Table;
	            File result = MotifTableMaker.makeTable(progressDialog.getStatusLabel(), 
	            		fastaFile, motifFile);
	            
	            // kill the dialog
	            progressDialog.dispose();

	            if (result == null) {
	            	JOptionPane.showMessageDialog(parent, "Error in building table");
	            }
	            else {
	            	// show the completion message box
	            	JOptionPane.showMessageDialog(parent, "Done!");
	            }
			}
		}).start();
	}
	
   private class ProgressDialog extends JDialog {

	      private JLabel messageLabel = new JLabel();

	      public ProgressDialog() {
	         super(parent);
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
