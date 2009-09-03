package com.bc.cisanalysis;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JLabel;
import com.bc.util.ResourceUtil;
import com.bc.chipenrich.service.*;
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
	public PrintWriter writer;
	private PrintWriter writer2;
	private PrintWriter nodeWriter;
	private String directory;
	private String patternDir;
	private PromomerTable pt;
	private JLabel status;
	private BindingSiteReader binding_site_read;
	private TFFReader families_read;
	
	public CISAnalyzer(JLabel status, String patternDir) {
		this.status = status;
		this.patternDir = patternDir;
		directory = patternDir + "/singletons";
		BackgroundChip bc;
		AGIMotifReader tableReader;
		ChipEnrichService ces = new ChipEnrichServiceImpl();
		try {
			bc = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream("SINGLETONS.txt"));
			tableReader = new AGIMotifReader(getClass().getClassLoader().getResourceAsStream("AGI_Motif_Table.txt"));
			binding_site_read = new BindingSiteReader(getClass().getClassLoader().getResourceAsStream("new_binding_site.txt"));
			families_read = new TFFReader(getClass().getClassLoader().getResourceAsStream("families_summary.txt"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		pt = new PromomerTableImpl(bc, tableReader);
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
			
			if (status != null) {
				status.setText("Analysis: " + patternName);
			}
			
			filename = patternName + ".significant.txt";
			File outFile = new File(outDir + "/" + filename);
			try {
				writer = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			outFile = new File(outDir + "/" + patternName + ".sub.tf.txt");
			try {
				writer2 = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			outFile = new File(outDir + "/" + patternName + ".node.txt");
			try {
				nodeWriter = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			nodeWriter.println(patternName + "\tpattern");
			
			new File(directory + "/subpatterns").mkdir();
			CISReader cisread = new CISReader(motifs[i], writer);
			Set<String> enrichedCIS = cisread.getSignificantMotifs();
			
			//Print node properties
			for (String cis : enrichedCIS) {
				nodeWriter.println(cis + "\tmotif");
			}

			//Get enriched gos;
			File GOfile = new File(directory + "/go/" + patternName + ".processed.txt");
			File GOAGIfile = new File(directory + "/go/" + patternName + ".processed.agi_list.txt");
			if (GOfile.exists() && GOAGIfile.exists()) {
				match_GO_CIS(patternName, GOfile, GOAGIfile, enrichedCIS);
			} else {
				System.out.println("GO File for " + patternName + " not found");
			}
			writer.close();
			writer2.close();
			nodeWriter.close();
		}
	}
	
	private void match_GO_CIS(String patternName, File gofile, File goAGIfile, Set<String> enrichedCIS) {
		ResultReader goreader = new ResultReader(gofile, goAGIfile, writer);
		GeneDescriptorMap<GO> gdMap = goreader.parseGOResults();
		Set<GO> GOs = gdMap.getGeneDescriptors();
		
		//Print node properties
		if (nodeWriter != null) {
			for (GO sigGO : GOs) {
				nodeWriter.println(sigGO.getDescription() + "\tGO");
			}
		}
		
		//Get all AGIs in pattern
		Set<AGI> patternAGIs = new HashSet<AGI>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(patternDir + "/" + patternName));
			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				try {
					patternAGIs.add(AGI.createAGI(nextLine.trim()));
				} catch (IllegalArgumentException e) {
				}
			}
		} catch (Exception e) {
			System.out.println(patternName);
			e.printStackTrace();
			return;
		}

		for (GO nextGO : GOs) {
			Set<AGI> queryList = gdMap.getAGIs(nextGO);
			MotifReader allMReader = null;

			PrintWriter subwriter = null;
			File outFile1 = new File(directory + "/subpatterns/" + nextGO.getId().replaceAll(":", "") + ".txt");
			try {
				subwriter = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile1)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			PrintWriter subAGIwriter = null;
			File outFile2 = new File(directory + "/subpatterns/" + nextGO.getId().replaceAll(":", "") + ".agi_list.txt");
			try {
				subAGIwriter = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile2)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			subwriter.println(nextGO.getId() + "\t" + nextGO.getDescription());

			try {
				allMReader = new MotifReader(getClass().getClassLoader().getResourceAsStream("element_name_and_motif_IUPAC.txt"));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			while (allMReader.nextLine()) {
				String nextMotif = allMReader.getMotif();
				String nextElement = allMReader.getElement();
				double pval = pt.parseLine(nextGO.getDescription(), nextMotif, nextElement, queryList, subwriter, subAGIwriter);
				if (pval < 0.001) {
					writer2.println(nextGO.getDescription()
							+ "\t" + nextElement
							+ "\t" + String.valueOf(Math.log10(pval)));
					if ((enrichedCIS != null) && (enrichedCIS.contains(nextElement))) {
						//Motif is significant in both pattern and subpattern
						writer.println(nextGO.getDescription()
								+ "\t" + nextElement
								+ "\t" + String.valueOf(Math.log10(pval)));
					}
//					look in binding_sites for associated TFF
					String TFF_name = binding_site_read.get(nextElement);
					Set<AGI> all_agis = null;
					if (TFF_name != null) {
						all_agis = families_read.get(TFF_name);
					}
//					look in families_summary for associated AGI_IDs
					if (all_agis != null) {
						for (AGI nextAGI : all_agis) {
//							if motif is significant in the pattern and the pattern contains the AGI
							if ((enrichedCIS != null) && (enrichedCIS.contains(nextElement))) {
								if (patternAGIs.contains(nextAGI)) {
									writer.println(nextAGI.getId() + "\t" + nextElement);
									nodeWriter.println(nextAGI.getId() + "\tTranscription Factor");
								}
							}
						}
					}
					else {
						System.out.println(TFF_name + " not in families_summary");
					}
				}
			}
			subwriter.close();
			subAGIwriter.close();
		}
		return;
	}
}
