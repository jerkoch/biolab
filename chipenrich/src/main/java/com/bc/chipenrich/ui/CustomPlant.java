package com.bc.chipenrich.ui;

public class CustomPlant {
	private String plantName;
	private String wholechip;
	private String singleton;
	private String tff;
	private String metabolic;
	private String bindingsite;
	private String motif;
	private String GOs;
	private String array;
	private String table;
	
	public CustomPlant() {
		this.plantName = null;
		this.wholechip = null;
		this.singleton = null;
		this.tff = null;
		this.metabolic = null;
		this.bindingsite = null;
		this.motif = null;
		this.GOs = null;
		this.array = null;
		this.table = null;
	}
	
	public void setName(String name) {
		this.plantName = name;
	}
	
	public void setWholechip(String wholechip) {
		this.wholechip = wholechip;
	}
	
	public void setSingleton(String singleton) {
		this.singleton = singleton;
	}
	
	public void setTFF(String tff) {
		this.tff = tff;
	}
	
	public void setMetabolic(String metabolic) {
		this.metabolic = metabolic;
	}
	
	public void setBindingSite(String bindingsite) {
		this.bindingsite = bindingsite;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public void setGOs(String GOs) {
		this.GOs = GOs;
	}
	
	public void setArray(String array) {
		this.array = array;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getName() {
		return plantName;
	}
	
	public String getWholechip() {
		return wholechip;
	}
	
	public String getSingleton() {
		return singleton;
	}
	
	public String getTFF() {
		return tff;
	}
	
	public String getMetabolic() {
		return metabolic;
	}
	
	public String getBindingSite() {
		return bindingsite;
	}
	
	public String getMotif() {
		return motif;
	}
	
	public String getGOs() {
		return GOs;
	}
	
	public String getArray() {
		return array;
	}
	
	public String getTable() {
		return table;
	}
}
