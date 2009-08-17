package com.bc.core;

public class AGIMotif {
	private AGI agi;
	private int[] motifCount;

	public AGIMotif(AGI agi, int[] motifCount) {
		this.agi = agi;
		this.motifCount = motifCount;
	}
	
	public AGI getAGI() {
		return agi;
	}
	
	public int getMotifCount(int i) {
		return motifCount[i];
	}
}
