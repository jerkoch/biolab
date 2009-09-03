package com.bc.core;

public class CIS extends GeneDescriptor implements Comparable<CIS>{
	public CIS(String motif, String element) {
		super(motif, element);
	}
	
	public int compareTo(CIS cis) {
		return id.compareTo(cis.getId());
	}
}
