package com.bc.chipenrich.ui.chooser;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.bc.chipenrich.ui.MotifFileLocator;

public class MotifChooser extends AbstractAction {
	private JLabel location;
	
	public MotifChooser(JLabel label) {
		this.location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Motif File...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			MotifFileLocator.getInstance().setExternalFile(chooser.getSelectedFile());
			location.setText("Using Custom Motifs");
		}
	}
}