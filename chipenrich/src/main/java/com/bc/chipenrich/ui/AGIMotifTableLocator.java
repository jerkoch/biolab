package com.bc.chipenrich.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.bc.chipenrich.ui.chooser.PlantChooser;

public class AGIMotifTableLocator {
	
	public static AGIMotifTableLocator INSTANCE = new AGIMotifTableLocator();
	
	private File externalFile;
	
	public static AGIMotifTableLocator getInstance() {
		return INSTANCE;
	}
	
	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream(
					PlantChooser.getInstance().getPlant() + "/AGI_Motif_Table.txt");
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
	}
}
