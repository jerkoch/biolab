package com.bc.chipenrich.ui.locator;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.bc.chipenrich.ui.chooser.PlantChooser;

public class MotifFileLocator extends AbstractAction {
	private static MotifFileLocator INSTANCE = new MotifFileLocator();
	
	private File externalFile;
	private JLabel location;
	
	public static MotifFileLocator getInstance() {
		return INSTANCE;
	}
	
	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream(
					PlantChooser.getInstance().getPlant() + "/element_name_and_motif_IUPAC.txt");
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
		location.setText(file.getAbsolutePath());
	}

	public void setLabel(JLabel label) {
		this.location = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Motif File...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setApproveButtonText("Select");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.setExternalFile(chooser.getSelectedFile());
		}
	}
}
