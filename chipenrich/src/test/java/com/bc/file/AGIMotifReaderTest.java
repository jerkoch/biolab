package com.bc.file;

import junit.framework.TestCase;

public class AGIMotifReaderTest extends TestCase{
	
	public void testRead() {
		try {
			AGIMotifReader reader = new AGIMotifReader();
			System.out.println("Test AGI Motif");
			System.out.println(reader.getCount("AT1G08510", "TTDCCWWWWWWGGH"));
			System.out.println(reader.getCount("AT1G08510", "TGTCTC"));
			System.out.println(reader.getCount("AT1G70150", "BACGTGKM"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
