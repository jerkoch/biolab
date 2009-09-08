package com.bc.chipenrich.ui;

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
	            chooser.setDialogTitle("Select TAIR Upstream File...");
	            chooser.setMultiSelectionEnabled(false);
	            chooser.setApproveButtonText("Select");
	            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
	            	return;
	            }
	            
                JFileChooser chooser2 = new JFileChooser();
                chooser2.setCurrentDirectory(new java.io.File("."));
                chooser2.setDialogTitle("Select motif file...");
                chooser2.setMultiSelectionEnabled(false);
                chooser2.setApproveButtonText("Select");
                if (chooser2.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                	return;
                }
				
		        // create and display the dialog
	            ProgressDialog progressDialog = new ProgressDialog();
	            progressDialog.setVisible(true);

	            // setup listener for close
	            progressDialog.addWindowListener(new WindowAdapter() {
	               public void windowClosing(WindowEvent aEvent) {
	               }
	            });
	            
	            //Make Table;
	            int result = MotifTableMaker.makeTable(progressDialog.getStatusLabel(), chooser.getSelectedFile(), chooser2.getSelectedFile());
	            
	            // kill the dialog
	            progressDialog.dispose();

	            if (result < 0) {
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
