package com.bc.chipenrich.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class BindingSiteLocator extends AbstractAction{
	private static BindingSiteLocator INSTANCE = new BindingSiteLocator();
	
	private File externalFile;
	private JLabel location;
	
	public static BindingSiteLocator getInstance() {
		return INSTANCE;
	}

	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream(
					"arabidopsis/matching_binding_site_no_spaces_2.txt");
		} else {
			try {
				return new FileInputStream(externalFile);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public void setExternalFile(File file) {
		externalFile = file;
		location.setText(file.getAbsolutePath());
	}
	
	public void setLabel(JLabel label) {
		this.location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Binding Site File...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.setExternalFile(chooser.getSelectedFile());
		}
	}
}