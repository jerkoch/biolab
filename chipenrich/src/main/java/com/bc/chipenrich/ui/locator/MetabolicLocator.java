package com.bc.chipenrich.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.AbstractAction;

import com.bc.chipenrich.ui.chooser.PlantChooser;

public class MetabolicLocator extends AbstractAction {
	private static MetabolicLocator INSTANCE = new MetabolicLocator();

	private File externalFile;
	private JLabel location;
	
	public static MetabolicLocator getInstance() {
		return INSTANCE;
	}

	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream(
					PlantChooser.getInstance().getPlant() + "/metabolicpathways2008.txt");
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
		location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Metabolic File...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.setExternalFile(chooser.getSelectedFile());
		}
	}
}