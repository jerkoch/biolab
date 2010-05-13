package com.bc.chipenrich.ui;

import java.util.Vector;
import com.bc.chipenrich.ui.config.*;
import com.bc.chipenrich.ui.locator.*;

public class CustomPlantManager implements SettingsListener {
	private static final String CFG_WHOLECHIP = ".wholechip";
	private static final String CFG_SINGLETON = ".singleton";
	private static final String CFG_TFF = ".tff";
	private static final String CFG_METABOLIC = ".metabolic";
	private static final String CFG_BINDINGSITE = ".bindingsite";
	private static final String CFG_MOTIF = ".motif";
	private static final String CFG_GO = ".go";
	private static final String CFG_ARRAY = ".array";
	private static final String CFG_TABLE = ".table";
	private static final String CFG_NONE = "NONE";
	private static final String CFG_PLANTS = "plants.all";
	
	private MainWindow mainWindow;
	private Vector<CustomPlant> customPlants;
	
	public CustomPlantManager(MainWindow main) {
		this.mainWindow = main;
		customPlants = new Vector<CustomPlant>();
		
		AGIMotifTableLocator.getInstance().setManager(this);
		BindingSiteLocator.getInstance().setManager(this);
		GOAnnotationLocator.getInstance().setManager(this);
		MetabolicLocator.getInstance().setManager(this);
		MotifFileLocator.getInstance().setManager(this);
		SingletonChipLocator.getInstance().setManager(this);
		TFFLocator.getInstance().setManager(this);
		WholeChipLocator.getInstance().setManager(this);
	}
	
	public void newCustomPlant(CustomPlant newPlant) {
		customPlants.add(newPlant);
		//FIXME: Sanity Checking on newPlant
		//FIXME: Reload settings
		mainWindow.updatePlants();
	}
	
	public Vector<CustomPlant> getCustomPlants() {
		return customPlants;
	}
	
	public CustomPlant getPlant(String name) {
		for (CustomPlant nextPlant : customPlants) {
			if (nextPlant.getName() == name) {
				return nextPlant;
			}
		}
		// Not Found
		return null;
	}
	
	public void loadSettings(LoadSettingsEvent event) {
		//Change MainWindow
		String plantNames = event.getString(CFG_PLANTS);
		if (plantNames != null) {
			String[] plants = plantNames.split(";");
			for (int i = 0; i < plants.length; i++) {
				if ((plants[i] != null) && (!plants[i].equals(""))) {
					CustomPlant nextPlant = new CustomPlant();
					nextPlant.setName(plants[i]);
					String array = event.getString(plants[i] + CFG_ARRAY);
					if (array != CFG_NONE) {
						nextPlant.setArray(array);
					}
					String wholechip = event.getString(plants[i] + CFG_WHOLECHIP);
					if (wholechip != CFG_NONE) {
						nextPlant.setWholechip(wholechip);
					}
					String singleton = event.getString(plants[i]+ CFG_SINGLETON);
					if (singleton != CFG_NONE) {
						nextPlant.setSingleton(singleton);
					}
					String tff = event.getString(plants[i]+ CFG_TFF);
					if (tff != CFG_NONE) {
						nextPlant.setTFF(tff);
					}
					String metabolic = event.getString(plants[i] + CFG_METABOLIC);
					if (metabolic != CFG_NONE) {
						nextPlant.setMetabolic(metabolic);
					}
					String bindingsite = event.getString(plants[i] + CFG_BINDINGSITE);
					if (bindingsite != CFG_NONE) {
						nextPlant.setBindingSite(bindingsite);
					}
					String motif = event.getString(plants[i] + CFG_MOTIF);
					if (motif != CFG_NONE) {
						nextPlant.setMotif(motif);
					}
					String go = event.getString(plants[i] + CFG_GO);
					if (go != CFG_NONE) {
						nextPlant.setGOs(go);
					}
					String table = event.getString(plants[i] + CFG_TABLE);
					if (table != CFG_NONE) {
						nextPlant.setTable(table);
					}
					customPlants.add(nextPlant);
				}
			}
		}
		mainWindow.updatePlants();
	}
	
	public void saveSettings(SaveSettingsEvent event) {
		//Save to file
		String plants = "";
		for (CustomPlant customPlant : customPlants) {
			if (customPlant != null) {
				String name = customPlant.getName();
				plants += name + ";";
				if (customPlant.getArray() != null) {
					event.saveSetting(name + CFG_ARRAY, customPlant.getArray());
				} else {
					event.saveSetting(name + CFG_ARRAY, CFG_NONE);
				}
				if (customPlant.getWholechip() != null) {
					event.saveSetting(name + CFG_WHOLECHIP, customPlant.getWholechip());
				} else {
					event.saveSetting(name + CFG_WHOLECHIP, CFG_NONE);
				}
				if (customPlant.getSingleton() != null) {
					event.saveSetting(name + CFG_SINGLETON, customPlant.getSingleton());
				} else {
					event.saveSetting(name + CFG_SINGLETON, CFG_NONE);
				}
				if (customPlant.getTFF() != null) {
					event.saveSetting(name + CFG_TFF, customPlant.getTFF());
				} else {
					event.saveSetting(name + CFG_TFF, CFG_NONE);
				}
				if (customPlant.getMetabolic() != null) {
					event.saveSetting(name + CFG_METABOLIC, customPlant.getMetabolic());
				} else {
					event.saveSetting(name + CFG_METABOLIC, CFG_NONE);
				}
				if (customPlant.getBindingSite() != null) {
					event.saveSetting(name + CFG_BINDINGSITE, customPlant.getBindingSite());
				} else {
					event.saveSetting(name + CFG_BINDINGSITE, CFG_NONE);
				}
				if (customPlant.getMotif() != null) {
					event.saveSetting(name + CFG_MOTIF, customPlant.getMotif());
				} else {
					event.saveSetting(name + CFG_MOTIF, CFG_NONE);
				}
				if (customPlant.getGOs() != null) {
					event.saveSetting(name + CFG_GO, customPlant.getGOs());
				} else {
					event.saveSetting(name + CFG_GO, CFG_NONE);
				}
				if (customPlant.getTable() != null) {
					event.saveSetting(name + CFG_TABLE, customPlant.getTable());
				} else {
					event.saveSetting(name + CFG_TABLE, CFG_NONE);
				}
			}
		}
		event.saveSetting(CFG_PLANTS, plants);
	}
}
