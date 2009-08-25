package com.bc.cisanalysis;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Set;
import java.util.Iterator;
import java.util.TreeSet;
import com.bc.util.ResourceUtil;
import com.bc.chipenrich.service.*;
import com.bc.chipenrich.domain.AGIQueryListParser;
import com.bc.core.BackgroundChip;
import com.bc.core.AGI;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;
import com.bc.file.AGIMotifReader;
import com.bc.file.MotifReader;
import com.bc.file.BindingSiteReader;
import com.bc.file.TFFReader;
import com.bc.promomer.service.PromomerTable;
import com.bc.promomer.service.PromomerTableImpl;

public class CISAnalyzer {
	private PrintStream writer;
	private String patternDir;
	private String directory;
	private PromomerTable pt;
	private BindingSiteReader binding_site_read;
	private TFFReader tffRead;
	
	public CISAnalyzer() {
		patternDir = "C:/Documents and Settings/Corey Harada/Desktop/Patterns";
		directory = patternDir + "/singletons";
		BackgroundChip bc;
		AGIMotifReader tableReader;
		ChipEnrichService ces = new ChipEnrichServiceImpl();
		try {
			bc = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream("SINGLETONS.txt"));
			tableReader = new AGIMotifReader(new FileInputStream("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/AGI_Motif_Table.txt"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		pt = new PromomerTableImpl(bc, tableReader);
		InputStream is = getClass().getClassLoader().getResourceAsStream("new_binding_site.txt");
		binding_site_read = new BindingSiteReader(is);
		is = getClass().getClassLoader().getResourceAsStream("families_summary.txt");
		tffRead = new TFFReader(is);
		is = null;
	}
	
	public void makeTable() {
		//For each pattern:
		//Print pattern + motif name
		//Print pattern + GO category
		//Print GO catogory + motifs
		File[] motifs = ResourceUtil.getFiles(directory + "/motifs", ".processed.txt");
		String outDir = directory + "/analysis";
		new File(outDir).mkdir();
		writer = null;
		for (int i = 0; i < motifs.length; i++) {
			String filename = motifs[i].getName();
			String patternName = filename.substring(0, filename.lastIndexOf(".processed.txt"));
			filename = patternName + ".significant.txt";
			File outFile = new File(outDir + "/" + filename);
			try {
				writer = new PrintStream(
						new FileOutputStream(outFile));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			CISReader cisread = new CISReader(motifs[i], writer);
			File tempMotif = cisread.getSignificantMotif();
			if (tempMotif != null) {
//				System.out.println("Found significant motifs for " + patternName);
				//Found significant motifs: move on to GO
				File[] GOfile = ResourceUtil.getFiles(directory + "/GO", patternName + ".processed.txt");
				File[] GOAGIfile = ResourceUtil.getFiles(directory + "/GO", patternName + ".processed.agi_list.txt");
				if ((GOfile.length == 1) && (GOAGIfile.length == 1)) {
//					Get motifs enriched in GO categories
					Set<String> GO_motifs = match_GO_CIS(GOfile[0], GOAGIfile[0], tempMotif);
					if (!GO_motifs.isEmpty()) {
						//Some cis elements are enriched in GO categories
						//Find corresponding TFFs
						match_CIS_TFF(patternName, GO_motifs);
					}
				} else {
					System.out.println("GO File for " + patternName + " not found");
				}
				writer.close();
				writer = null;
				//Now print which motifs are enriched in the pattern
				outFile = null;
				outFile = new File(outDir + "/" + patternName + ".tf.txt");
				try {
					writer = new PrintStream(
							new FileOutputStream(outFile));
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				match_CIS_TFF(patternName, cisread.returnSignificantMotifs());
				//Now print motifs which are enriched in the subpattern
				
				//////////////////////////////////////////////////////////////
				writer.close();
				writer = null;
				
				outFile = null;
				outFile = new File(outDir + "/" + patternName + ".sub.tf.txt");
				try {
					writer = new PrintStream(
							new FileOutputStream(outFile));
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				File[] CISGOs = ResourceUtil.getFiles("C:/Documents and Settings/Corey Harada/Desktop/pattern_results/GO", ".txt.processed.txt");
				for (int j = 0; j < CISGOs.length; j++) {
					if (CISGOs[j].getName().startsWith(patternName)) {
						CISReader tempCISread = new CISReader(CISGOs[j], null);
						tempCISread.getSignificantMotif();
						match_CIS_TFF(patternName, tempCISread.returnSignificantMotifs());
					}
				}
				///////////////////////////////////////////////////////////////
				
			} else {
				//No significant motifs in pattern
//				System.out.println("No significant motifs in " + patternName);
				writer.close();
				outFile.delete();
			}
		}
	}
	
	public Set<String> match_GO_CIS(File gofile, File goAGIfile, File tempMotif) {
		Set<String> motif_names = new TreeSet<String>();
		ResultReader goreader = new ResultReader(gofile, goAGIfile, writer);
		GeneDescriptorMap<GO> gdMap = goreader.parseGOResults();
		Set<GO> GOs = gdMap.getGeneDescriptors();
		Iterator<GO> it = GOs.iterator();
		while (it.hasNext()) {
			GO nextGO = it.next();
			Set<AGI> queryList = gdMap.getAGIs(nextGO);
			MotifReader mReader;
			try {
				mReader = new MotifReader(new FileInputStream(tempMotif));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			while (mReader.nextLine()) {
				String nextMotif = mReader.getMotif();
				String nextElement = mReader.getElement();
				double pval = pt.parseLine(nextMotif, nextElement, queryList, null, null);
				if (pval < 0.001) {
					writer.println(nextGO.getDescription() + "\t" + nextElement);
					motif_names.add(nextElement);
				}
			}
		}
		return motif_names;
	}
	
	public void match_CIS_TFF(String patternName, Set<String> motifs) {
		File[] tffs = ResourceUtil.getFiles(directory + "/tff", patternName + ".processed.txt");
		if (tffs.length != 1) {
			System.out.println("Problem with TFF files");
			return;
		}
		File[] pattern = ResourceUtil.getFiles(patternDir, patternName);
		if (pattern.length != 1) {
			System.out.println("Problem with pattern file");
			return;
		}
		AGIQueryListParser patternParser = new AGIQueryListParser();
		BufferedReader readTFFFile;
		Set<String> TFF_in_pattern = new TreeSet<String>();
		try {
			patternParser.parse(new FileInputStream(pattern[0]));
			readTFFFile = new BufferedReader(new FileReader(tffs[0]));
			//Get all TFFs in the pattern
			String nextLine;
			while ((nextLine = readTFFFile.readLine()) != null) {
				String TFF = nextLine.substring(0, nextLine.indexOf('\t')).trim();
				TFF_in_pattern.add(TFF);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Set<AGI> agis_in_pattern = patternParser.getAGIs();
		Iterator<String> it = motifs.iterator();
		while (it.hasNext()) {
			String nextElement = it.next();
			String nextTFF = binding_site_read.get(nextElement);
			//Check if nextTFF is present in the pattern
			if (nextTFF == null) {
				System.out.println(nextElement + " not found in binding_site file");
			}
			if ((nextTFF != null) && (TFF_in_pattern.contains(nextTFF))) {
				//Get AGI IDs corresponding to TFF
				Set<AGI> agis = tffRead.get(nextTFF);
				if (agis != null) {
					Iterator<AGI> agiIt = agis.iterator();
					while (agiIt.hasNext()) {
						AGI nextAGI = agiIt.next();
						if (agis_in_pattern.contains(nextAGI)) {
							writer.println(nextTFF + "\t" + nextAGI.getId());
						}
					}
				}
				else {
					System.out.println(nextTFF + " not found in families_summary");
				}
			}
		}
	}
	
	///////////////////////////////////////////////
	public void match_CIS_TFF2(String patternName, Set<String> motifs) {
		File[] tffs = ResourceUtil.getFiles(directory + "/tff", patternName + ".processed.txt");
		if (tffs.length != 1) {
			System.out.println("Problem with TFF files");
			return;
		}
		File[] pattern = ResourceUtil.getFiles(patternDir, patternName);
		if (pattern.length != 1) {
			System.out.println("Problem with pattern file");
			return;
		}
		BufferedReader readTFFFile;
		Set<String> TFF_in_pattern = new TreeSet<String>();
		try {
			readTFFFile = new BufferedReader(new FileReader(tffs[0]));
			//Get all TFFs in the pattern
			String nextLine;
			while ((nextLine = readTFFFile.readLine()) != null) {
				String TFF = nextLine.substring(0, nextLine.indexOf('\t')).trim();
				TFF_in_pattern.add(TFF);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Iterator<String> it = motifs.iterator();
		while (it.hasNext()) {
			String nextElement = it.next();
			String nextTFF = binding_site_read.get(nextElement);
			//Check if nextTFF is present in the pattern
			if (nextTFF == null) {
				System.out.println(nextElement + " not found in binding_site file");
			}
			if ((nextTFF != null) && (TFF_in_pattern.contains(nextTFF))) {
				//Get AGI IDs corresponding to TFF
				Set<AGI> agis = tffRead.get(nextTFF);
				if (agis != null) {
					Iterator<AGI> agiIt = agis.iterator();
					while (agiIt.hasNext()) {
						AGI nextAGI = agiIt.next();
						writer.println(nextTFF + "\t" + nextAGI.getId());
					}
				}
				else {
					System.out.println(nextTFF + " not found in families_summary");
				}
			}
		}
	}
}
