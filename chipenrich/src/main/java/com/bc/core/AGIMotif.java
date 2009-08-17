package com.bc.core;

public class AGIMotif {
	private String agi;
	private int[] motifCount;

	public AGIMotif(String agi, int[] motifCount) {
		this.agi = agi;
		this.motifCount = motifCount;
	}
	
	public String getAGI() {
		return agi;
	}
	
	public int getMotifCount(int i) {
		return motifCount[i];
	}
}
