package com.bc.chipenrich.ui.chooser;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.bc.chipenrich.ui.GOAnnotationLocator;

public class GOChooser extends AbstractAction {
	private JLabel location;
	
	public GOChooser(JLabel label) {
		this.location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select GO Annotation File(s)...");
		chooser.setMultiSelectionEnabled(true);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			GOAnnotationLocator.getInstance().setExternalFiles(chooser.getSelectedFiles());
			location.setText("Using Custom GOs");
		}
	}
}
