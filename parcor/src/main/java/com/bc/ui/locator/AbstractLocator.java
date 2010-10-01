package com.bc.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public abstract class AbstractLocator extends AbstractAction {
	private File externalFile;
	private JLabel location;
	
	protected abstract String getDefault();
	
	public InputStream getInputStream() {
		if (externalFile == null) {
			if (getDefault() != null) {
				return getClass().getClassLoader().getResourceAsStream(getDefault());
			} else return null;
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
		this.externalFile = file;
		if (location != null) {
			location.setText(file.getAbsolutePath());
		}
	}
	
	public void setLabel(JLabel label) {
		this.location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select " + getType() + " File...");
		chooser.setMultiSelectionEnabled(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.setExternalFile(chooser.getSelectedFile());
		}
	}
	
	protected abstract String getType();
}

