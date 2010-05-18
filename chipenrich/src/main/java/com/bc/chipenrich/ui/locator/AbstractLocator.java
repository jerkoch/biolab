package com.bc.chipenrich.ui.locator;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.bc.chipenrich.ui.chooser.PlantChooser;
import com.bc.chipenrich.ui.CustomPlantManager;
import com.bc.chipenrich.ui.CustomPlant;

public abstract class AbstractLocator extends AbstractAction {
	private File externalFile;
	private CustomPlantManager plantManager;
	private JLabel location;
	
	protected abstract String getArabidopsisDefault();
	
	protected abstract String getSoybeanDefault();

	public void setManager(CustomPlantManager manager) {
		this.plantManager = manager;
	}
	
	public InputStream getInputStream() {
		if (externalFile == null) {
			if (PlantChooser.getInstance().getPlant().equals("arabidopsis")) {
				if (getArabidopsisDefault() != null) {
					return getClass().getClassLoader().getResourceAsStream(getArabidopsisDefault());
				} else return null;
			}
			else if (PlantChooser.getInstance().getPlant().equals("soybean")) {
				if (getSoybeanDefault() != null) {
					return getClass().getClassLoader().getResourceAsStream(getSoybeanDefault());
				} else return null;
			}
			else {	//Custom Plant
				CustomPlant plant = plantManager.getPlant(PlantChooser.getInstance().getPlant());
				if (plant != null) {
					String location = getField(plant);
					if (location != null) {
						try {
							return new FileInputStream(location);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
					else return null;	// Field not specified for custom plant
				}
				else return null;	//Custom Plant not found
			}
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
	
	protected abstract String getField(CustomPlant plant);
	protected abstract String getType();
}
