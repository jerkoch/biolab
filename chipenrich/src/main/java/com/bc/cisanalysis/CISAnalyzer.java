package com.bc.cisanalysis;

import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import com.bc.util.ResourceUtil;

public class CISAnalyzer {
	public CISAnalyzer() {
		
	}
	
	public static void main(String[] args) {
		//For each pattern:
		//Print pattern + motif name
		//Print pattern + GO category
		//Print go catogory + motifs
		String directory = "C:/Documents and Settings/Corey Harada/Desktop/Patterns/singletons";
		File[] motifs = ResourceUtil.getFiles(directory + "/motifs", ".processed.txt");
		String outDir = directory + "/analysis";
		new File(outDir).mkdir();
		PrintStream writer = null;
		for (int i = 0; i < motifs.length; i++) {
			String filename = motifs[i].getName();
			String patternName = filename.substring(0, filename.lastIndexOf(".processed.txt"));
			filename = patternName + ".significant.txt";
			File outFile = new File(outDir + "/" + filename);
			try {
				writer = new PrintStream(
						new FileOutputStream(outFile));
			} catch (Exception e) {
				return;
			}
			CISReader cisread = new CISReader(motifs[i], writer);
			File tempMotif = cisread.getSignificantMotif();
			if (tempMotif != null) {
				//Found significant motifs: move on to GO
				File[] GOfile = ResourceUtil.getFiles(directory + "/GO", patternName + ".processed.txt");
				File[] GOAGIfile = ResourceUtil.getFiles(directory + "/GO", patternName + ".processed.agi_list.txt");
				if ((GOfile.length == 1) && (GOAGIfile.length == 1)) {
					ResultReader goreader = new ResultReader(GOfile[0], GOAGIfile[0], writer);
					goreader.parseGOResults();
				} else {
					System.out.println("GO File for " + patternName + " not found");
				}
				writer.close();
			}
			else {
				//No significant motifs in pattern
				tempMotif.delete();
				tempMotif = null;
				writer.close();
				outFile.delete();
			}
		}
//		ResultReader rr = new ResultReader();
//		rr.parseGOResults();
	}
}
