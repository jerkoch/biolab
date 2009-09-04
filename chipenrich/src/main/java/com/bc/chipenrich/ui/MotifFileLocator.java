package com.bc.chipenrich.ui;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

public class MotifFileLocator {
	
	private static MotifFileLocator INSTANCE = new MotifFileLocator();
	
	private File externalFile;
	
	public static MotifFileLocator getInstance() {
		return INSTANCE;
	}
	
	public InputStream getInputStream() {
		if (externalFile == null) {
			return getClass().getClassLoader().getResourceAsStream("element_name_and_motif_IUPAC.txt");
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
