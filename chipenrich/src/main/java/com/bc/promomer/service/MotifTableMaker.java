package com.bc.promomer.service;

import java.io.*;

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
	
	public static void makeTable(File upstreamFile, File motifFile) {
		PrintWriter p;
		try {
			String outName = "AGI_Motif_Table";
			File outFile = new File(outName + ".txt");
			int append = 1;
			while (outFile.exists()) {
				outFile = new File(outName + String.valueOf(append) + ".txt");
				append++;
			}
			p = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

			UpstreamReader reader = new UpstreamReader(new FileInputStream(upstreamFile));
			MotifReader mReader = new MotifReader(new FileInputStream(motifFile));
			
			//build first row
			p.print('\t');
			String nextMotif;
			while ((nextMotif = mReader.getMotif()) != null) {
				p.print(nextMotif);
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
				mReader = new MotifReader(new FileInputStream(motifFile));
				while ((nextMotif = mReader.getMotif()) != null) {
					num = CisMatcherUtil.getCisCount(nextMotif, agiUp.getUpstream());
					p.print(num);
					p.print('\t');
				}
				p.print('\n');
				mReader.close();
			}
			
			p.close();
		}
		catch (Exception e) {
			System.out.println("Error");
			return;
		}
	}
}