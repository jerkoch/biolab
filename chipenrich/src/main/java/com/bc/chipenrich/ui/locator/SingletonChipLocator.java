package com.bc.chipenrich.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.bc.chipenrich.ui.chooser.PlantChooser;

public class SingletonChipLocator extends AbstractAction {
	private static SingletonChipLocator INSTANCE = new SingletonChipLocator();
	
	private JLabel location;
	private File externalFile;
	
	public static SingletonChipLocator getInstance() {
		return INSTANCE;
	}
	
	public void setLabel(JLabel label) {
		this.location = label;
	}

	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream(
					PlantChooser.getInstance().getPlant() + "/SINGLETONS.txt");
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
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select singleton chip file...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.setExternalFile(chooser.getSelectedFile());
		}
	}
}