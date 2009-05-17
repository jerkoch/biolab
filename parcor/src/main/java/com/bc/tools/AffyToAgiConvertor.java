package com.bc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;

import javax.swing.JFileChooser;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.text.StrBuilder;

import com.bc.common.core.AGI;
import com.bc.common.core.Affy;
import com.bc.common.util.CSVParser;
import com.bc.path.AffyAgiParser;

public class AffyToAgiConvertor {

	private static final String DEFAULT_AFFY_AGI_FILE = "affy_ATH1_array_elements-2008-5-29.txt";
	private static final String NEW_SUBDIR = "agis";

	public static void main(String[] args) {
		AffyAgiParser parser = new AffyAgiParser(ClassLoader.getSystemResourceAsStream(DEFAULT_AFFY_AGI_FILE));

		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.showOpenDialog(null);

		File[] files = fc.getSelectedFiles();
		if (files != null) {
			// create the subdir for new files
			File subDir = new File(FilenameUtils.getFullPath(files[0].getAbsolutePath()) + NEW_SUBDIR);
			if (!subDir.exists()) {
				subDir.mkdir();
			}

			// loop through the file to convert
			for (int i = 0; i < files.length; i++) {
				try {
					FileWriter fw = new FileWriter(new File(subDir, files[i].getName()));
					PrintWriter pw = new PrintWriter(fw);

					CSVParser csvParser = new CSVParser(new FileInputStream(files[i]), "\t");
					while (csvParser.moreLines()) {
						// ensure we get a list of AGIs back 
						Collection<AGI> agis = parser.getAGIs(Affy.createAffy(csvParser.nextToken()));
						if (agis != null) {
							// build the agi output string (including multiples)
							StrBuilder str = new StrBuilder();
							for (AGI agi : agis) {
								str.appendSeparator(';');
								str.append(agi.toString());
							}
							pw.println(str.toString());
						}
					}
					pw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
