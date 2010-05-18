package com.bc.core;

public class AGIMotif {
	private String agi;
	private short[] motifCount;

	public AGIMotif(String agi, short[] motifCount) {
		this.agi = agi;
		this.motifCount = motifCount;
	}
	
	public String getAGI() {
		return agi;
	}
	
	public int getMotifCount(int i) {
		return (int) motifCount[i];
	}
}
