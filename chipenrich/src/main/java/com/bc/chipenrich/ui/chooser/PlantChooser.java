package com.bc.chipenrich.ui.chooser;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.bc.chipenrich.ui.CustomPlantManager;

public class PlantChooser extends AbstractAction {
	public static PlantChooser INSTANCE = new PlantChooser();
	
	private String chosenPlant = "arabidopsis";
	
	public static PlantChooser getInstance() {
		return INSTANCE;
	}
	
	public void setPlant(String newPlant) {
		chosenPlant = newPlant;
	}
	
	public String getPlant() {
		return chosenPlant;
	}
	
	public void actionPerformed(ActionEvent e) {
		setPlant(e.getActionCommand());
	}
}
