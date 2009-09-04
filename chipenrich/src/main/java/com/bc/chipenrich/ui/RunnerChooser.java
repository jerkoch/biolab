package com.bc.chipenrich.ui;

public class RunnerChooser {
	
	public static RunnerChooser INSTANCE = new RunnerChooser();
	
	private boolean ATH1Array = false;
	private boolean ATH1GO = false;
	private boolean ATH1Metabolic = false;
	private boolean ATH1TFF = false;
	private boolean ATH1Motif = false;
	private boolean ATH1Analysis = false;
	
	private boolean singletonArray = true;
	private boolean singletonGO = true;
	private boolean singletonMetabolic = true;
	private boolean singletonTFF = true;
	private boolean singletonMotif = true;
	private boolean singletonAnalysis = true;
	
	public static RunnerChooser getInstance() {
		return INSTANCE;
	}
	
	public void setATH1Array(boolean b) {
		ATH1Array = b;
	}
	public void setATH1GO(boolean b) {
		ATH1GO = b;
	}
	public void setATH1Metabolic(boolean b) {
		ATH1Metabolic = b;
	}
	public void setATH1TFF(boolean b) {
		ATH1TFF = b;
	}
	public void setATH1Motif(boolean b) {
		ATH1Motif = b;
	}
	public void setATH1Analysis(boolean b) {
		ATH1Analysis = b;
	}
	
	public void setSingletonArray(boolean b) {
		singletonArray = b;
	}
	public void setSingletonGO(boolean b) {
		singletonGO = b;
	}
	public void setSingletonMetabolic(boolean b) {
		singletonMetabolic = b;
	}
	public void setSingletonTFF(boolean b) {
		singletonTFF = b;
	}
	public void setSingletonMotif(boolean b) {
		singletonMotif = b;
	}
	public void setSingletonAnalysis(boolean b) {
		singletonAnalysis = b;
	}
	
	public boolean getATH1Array()	{			return ATH1Array;	}
	public boolean getATH1GO()		{			return ATH1GO;	}
	public boolean getATH1Metabolic()	{		return ATH1Metabolic;	}
	public boolean getATH1TFF()		{			return ATH1TFF;	}
	public boolean getATH1Motif()	{			return ATH1Motif;	}
	public boolean getATH1Analysis()	{		return ATH1Analysis;	}
	public boolean getSingletonArray()	{		return singletonArray;	}
	public boolean getSingletonGO()	{			return singletonGO;	}
	public boolean getSingletonMetabolic()	{	return singletonMetabolic;	}	
	public boolean getSingletonTFF()	{		return singletonTFF;	}
	public boolean getSingletonMotif()	{		return singletonMotif;	}
	public boolean getSingletonAnalysis()	{	return singletonAnalysis;	}
}
