package com.bc.file;

import java.io.FileInputStream;
import com.bc.core.AGI;

import junit.framework.TestCase;

public class AGIMotifReaderTest extends TestCase{
	
	public void testRead() {
		try {
			AGIMotifReader reader = new AGIMotifReader(
				new FileInputStream("C:/Documents and Settings/Corey Harada/My Documents/workspace/chipenrich/AGI_Motif_Table.txt"));
			System.out.println("Test AGI Motif");
			System.out.println(reader.getCount(AGI.createAGI("AT1G08510"), "TTDCCWWWWWWGGH"));
			System.out.println(reader.getCount(AGI.createAGI("AT1G08510"), "TGTCTC"));
			System.out.println(reader.getCount(AGI.createAGI("AT1G70150"), "BACGTGKM"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
