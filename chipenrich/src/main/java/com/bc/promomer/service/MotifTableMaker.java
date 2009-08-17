package com.bc.promomer.service;

import java.io.*;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.core.AGIUpstream;
import com.bc.core.BackgroundChip;
import com.bc.file.UpstreamReader;
import com.bc.util.CisMatcherUtil;
import com.bc.file.MotifReader;

public class MotifTableMaker {
	public MotifTableMaker() {
		try {
			ChipEnrichService ces = new ChipEnrichServiceImpl();
			BackgroundChip backgroundChip = ces.processBackgroundChip(new FileInputStream(new File("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/src/main/resources/ATH1Chip.txt")));
			
			makeTable(backgroundChip, new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/for siobhan/TAIR9_upstream_1000_20090619.txt"), 
					new File("C:/Documents and Settings/Corey Harada/Desktop/Mac/for siobhan/element_name_and_motif_IUPAC.txt"));
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new MotifTableMaker();
	}
	
	public void makeTable(BackgroundChip backgroundChip, File upstreamFile,
				File motifFile) {
		PrintWriter p;
		try {
			p = new PrintWriter(new BufferedWriter(new FileWriter("AGI_Motif_Table.txt")));

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