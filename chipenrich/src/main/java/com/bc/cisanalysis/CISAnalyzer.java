package com.bc.cisanalysis;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.Iterator;
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
	private String directory;
	private PromomerTable pt;
	private BindingSiteReader binding_site_read;
	private TFFReader families_read;
	private JLabel status;
	
	public CISAnalyzer(JLabel status, String patternDir) {
		this.status = status;
		directory = patternDir + "/singletons";
		BackgroundChip bc;
		AGIMotifReader tableReader;
		ChipEnrichService ces = new ChipEnrichServiceImpl();
		try {
			bc = ces.processBackgroundChip(getClass().getClassLoader().getResourceAsStream("SINGLETONS.txt"));
			tableReader = new AGIMotifReader(getClass().getClassLoader().getResourceAsStream("AGI_Motif_Table.txt"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		pt = new PromomerTableImpl(bc, tableReader);
		InputStream is = getClass().getClassLoader().getResourceAsStream("new_binding_site.txt");
		binding_site_read = new BindingSiteReader(is);
		is = getClass().getClassLoader().getResourceAsStream("families_summary.txt");
		families_read = new TFFReader(is);
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
			outFile = null;
			outFile = new File(outDir + "/" + patternName + ".sub.tf.txt");
			try {
				writer2 = new PrintWriter(new BufferedWriter(
						new FileWriter(outFile)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			CISReader cisread = new CISReader(motifs[i], writer);
			cisread.getSignificantMotif();
			Set<String> enrichedCIS = cisread.returnSignificantMotifs();
			//Find enriched GOs
/*
//			File[] GOfile = ResourceUtil.getFiles(directory + "/go", patternName + ".processed.txt");
//			File[] GOAGIfile = ResourceUtil.getFiles(directory + "/go", patternName + ".processed.agi_list.txt");
//			if ((GOfile.length == 1) && (GOAGIfile.length == 1)) {
//				Get motifs enriched in GO categories
//				match_GO_CIS(GOfile[0], GOAGIfile[0], tempMotif);
*/
			File GOfile = new File(directory + "/go/" + patternName + ".processed.txt");
			File GOAGIfile = new File(directory + "/go/" + patternName + ".processed.agi_list.txt");
			if (GOfile.exists() && GOAGIfile.exists()) {
				match_GO_CIS(patternName, GOfile, GOAGIfile, enrichedCIS);
			} else {
				System.out.println("GO File for " + patternName + " not found");
			}
			writer.close();
			writer2.close();
			writer = null;
			writer2 = null;
		}
	}
	
	public void match_GO_CIS(String patternName, File gofile, File goAGIfile, Set<String> enrichedCIS) {
		ResultReader goreader = new ResultReader(gofile, goAGIfile, writer);
		GeneDescriptorMap<GO> gdMap = goreader.parseGOResults();
		Set<GO> GOs = gdMap.getGeneDescriptors();
		Iterator<GO> it = GOs.iterator();
		while (it.hasNext()) {
			GO nextGO = it.next();
			Set<AGI> queryList = gdMap.getAGIs(nextGO);
			MotifReader allMReader = null;
			try {
				allMReader = new MotifReader(getClass().getClassLoader().getResourceAsStream("element_name_and_motif_IUPAC.txt"));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			while (allMReader.nextLine()) {
				String nextMotif = allMReader.getMotif();
				String nextElement = allMReader.getElement();
				double pval = pt.parseLine(nextMotif, nextElement, queryList, null, null);
				if (pval < 0.001) {
					writer2.println(nextGO.getDescription() + "\t" + nextElement);
					if ((enrichedCIS != null) && (enrichedCIS.contains(nextElement))) {
						//Motif is significant in both pattern and subpattern
						writer.println(nextGO.getDescription() + "\t" + nextElement);
					}
//					look in binding_sites for associated TFF
					String TFF_name = binding_site_read.get(nextElement);
//					look in families_summary for associated AGI_IDs
					Set<AGI> all_agis = families_read.get(TFF_name);
					if (all_agis != null) {
						Iterator<AGI> AGIit = all_agis.iterator();
						while (AGIit.hasNext()) {
							AGI nextAGI = AGIit.next();
							//if AGI is in GO category
							if (queryList.contains(nextAGI)) {
								writer.println(nextElement + "\t" + nextAGI.getId());
								//if agi is also in pattern
							}
						}
					}
					else {
						System.out.println(TFF_name + " not in families_summary");
					}
				}
			}
		}
		return;
	}
}
