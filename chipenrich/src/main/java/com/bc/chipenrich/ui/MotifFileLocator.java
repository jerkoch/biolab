package com.bc.chipenrich.ui;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import com.bc.chipenrich.ui.chooser.PlantChooser;

public class MotifFileLocator {
	
	private static MotifFileLocator INSTANCE = new MotifFileLocator();
	
	private File externalFile;
	
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
	}
}
