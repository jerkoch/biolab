package com.bc.promomer.service;

import java.io.*;
import javax.swing.JLabel;

import com.bc.core.AGIUpstream;
import com.bc.file.UpstreamReader;
import com.bc.util.CisMatcherUtil;
import com.bc.file.MotifReader;

/*
 * This class constructs a table containing the number of instances a motif
 * appears in a given upstream file.  If using a different set of motifs or
 * upstream file, the table should be reconstructed using the makeTable 
 * class.
 */
public class MotifTableMaker {
	
	public static int makeTable(JLabel status, File fastaFile, File motifFile) {
		PrintWriter p;
		try {
			status.setText("Build Table");
			String outName = "AGI_Motif_Table";
			File outFile = new File(outName + ".txt");
			int append = 1;
			while (outFile.exists()) {
				outFile = new File(outName + String.valueOf(append) + ".txt");
				append++;
			}
			p = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

			UpstreamReader reader = new UpstreamReader(new FileInputStream(fastaFile));
			MotifReader mReader = new MotifReader(new FileInputStream(motifFile));
			
			status.setText("Build Table: Reading Motifs");
			//build first row
			p.print('\t');
			while (mReader.nextLine()) {
				p.print(mReader.getMotif());
				p.print('\t');
			}
			p.print('\n');
			mReader.close();
			
			//build remaining rows
			
			AGIUpstream agiUp;
			int num = 0;
			
			while ((agiUp = reader.nextAGIUpstream()) != null) {
				p.print(agiUp.getAgi().getId());
				p.print('\t');
				status.setText("Build Table: " + agiUp.getAgi().getId());
				mReader = new MotifReader(new FileInputStream(motifFile));
				while (mReader.nextLine()) {
					String nextMotif = mReader.getMotif();
					num = CisMatcherUtil.getCisCount(nextMotif, agiUp.getUpstream());
					p.print(num);
					p.print('\t');
				}
				p.print('\n');
				mReader.close();
			}
			
			p.close();
			return 0;
		}
		catch (Exception e) {
			System.out.println("Error");
			return -1;
		}
	}
}