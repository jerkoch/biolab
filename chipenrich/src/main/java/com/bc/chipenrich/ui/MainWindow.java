package com.bc.chipenrich.ui;

import com.bc.chipenrich.ui.chooser.*;
import com.bc.chipenrich.ui.config.SettingsManager;
import com.bc.chipenrich.ui.locator.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainWindow extends JPanel  {
	private CustomPlantManager plantManager = new CustomPlantManager(this);
	private JPanel plantSelectorPanel;
	
	public MainWindow() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		SettingsManager.getInstance().addSettingsListener(plantManager);
		makeMenus();
	}

	private void makeMenus() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(makePlantPanel());
		makeSelectorPanel(topPanel);

		this.add(topPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		makeWholechipPanel(bottomPanel);
		makeSingletonPanel(bottomPanel);
		this.add(bottomPanel);
	}

	private JPanel makePlantPanel() {
		JPanel plantChooser = new JPanel();
		
		plantChooser.setLayout(new BoxLayout(plantChooser, BoxLayout.Y_AXIS));

		//JButton specify custom plant
		JButton newPlant = new JButton("Create custom plant");
		newPlant.addActionListener(new NewPlantAction(this, plantManager));
		plantChooser.add(newPlant);
		
		plantSelectorPanel = new JPanel();
		plantSelectorPanel.setLayout(new BoxLayout(plantSelectorPanel, BoxLayout.Y_AXIS));

		plantChooser.add(plantSelectorPanel);
		return plantChooser;
	}
	
	public void updatePlants() {
		plantSelectorPanel.removeAll();
		JRadioButton arabidopsisButton = new JRadioButton("arabidopsis");
		arabidopsisButton.setActionCommand("arabidopsis");
		arabidopsisButton.addActionListener(PlantChooser.getInstance());
		arabidopsisButton.setSelected(true);
		JRadioButton soybeanButton = new JRadioButton("soybean");
		soybeanButton.setActionCommand("soybean");
		soybeanButton.addActionListener(PlantChooser.getInstance());
		
		ButtonGroup plants = new ButtonGroup();
		plants.add(arabidopsisButton);
		plants.add(soybeanButton);

		plantSelectorPanel.add(arabidopsisButton);
		plantSelectorPanel.add(soybeanButton);		

		// Add custom plants
		Vector<CustomPlant> newPlants = plantManager.getCustomPlants();
		for (CustomPlant nextPlant : newPlants) {
			String newName = nextPlant.getName();
			JRadioButton customButton = new JRadioButton(newName);
			customButton.setActionCommand(newName);
			customButton.addActionListener(PlantChooser.getInstance());
			plants.add(customButton);
			plantSelectorPanel.add(customButton);
		}
		plantSelectorPanel.revalidate();
	}

	private void makeSelectorPanel(JPanel menu) {
		JPanel selector = new JPanel();
		selector.setLayout(new BoxLayout(selector, BoxLayout.Y_AXIS));
		//GOs
		JPanel GOs = new JPanel();
		GOs.setLayout(new BoxLayout(GOs, BoxLayout.X_AXIS));
		JLabel GOLabel = new JLabel("Using Default GOs");
		JButton GOButton = new JButton("Use Custom GO Files");
		GOButton.addActionListener(GOAnnotationLocator.getInstance());
		GOAnnotationLocator.getInstance().setLabel(GOLabel);
		GOs.add(GOButton);
		GOs.add(GOLabel);
		//Motif
		JPanel motifs = new JPanel();
		motifs.setLayout(new BoxLayout(motifs, BoxLayout.X_AXIS));
		JLabel motifLabel = new JLabel("Using Default Motifs");
		JButton motifButton = new JButton("Use Custom Motif Files");
		motifButton.addActionListener(MotifFileLocator.getInstance());
		MotifFileLocator.getInstance().setLabel(motifLabel);
		motifs.add(motifButton);
		motifs.add(motifLabel);
		//BindingSite
		JPanel bindingSite = new JPanel();
		bindingSite.setLayout(new BoxLayout(bindingSite, BoxLayout.X_AXIS));
		JLabel bindingSiteLabel = new JLabel("Using Default Binding Site");
		JButton bindingSiteButton = new JButton("Use Custom Binding Site File");
		bindingSiteButton.addActionListener(BindingSiteLocator.getInstance());
		BindingSiteLocator.getInstance().setLabel(bindingSiteLabel);
		bindingSite.add(bindingSiteButton);
		bindingSite.add(bindingSiteLabel);
		//Metabolic
		JPanel metabolic = new JPanel();
		metabolic.setLayout(new BoxLayout(metabolic, BoxLayout.X_AXIS));
		JLabel metabolicLabel = new JLabel("Using Default Metabolic");
		JButton metabolicButton = new JButton("Use Custom Metabolic File");
		metabolicButton.addActionListener(MetabolicLocator.getInstance());
		MetabolicLocator.getInstance().setLabel(metabolicLabel);
		metabolic.add(metabolicButton);
		metabolic.add(metabolicLabel);
		//TFF
		JPanel tff = new JPanel();
		tff.setLayout(new BoxLayout(tff, BoxLayout.X_AXIS));
		JLabel tffLabel = new JLabel("Using Default TFF");
		JButton tffButton = new JButton("Use Custom TFF File");
		tffButton.addActionListener(TFFLocator.getInstance());
		TFFLocator.getInstance().setLabel(tffLabel);
		tff.add(tffButton);
		tff.add(tffLabel);
		//Whole Chip
		JPanel wholechip = new JPanel();
		wholechip.setLayout(new BoxLayout(wholechip, BoxLayout.X_AXIS));
		JLabel wholechipLabel = new JLabel("Using Default Whole Chip");
		JButton wholechipButton = new JButton("Use Custom Whole Chip");
		wholechipButton.addActionListener(WholeChipLocator.getInstance());
		WholeChipLocator.getInstance().setLabel(wholechipLabel);
		wholechip.add(wholechipButton);
		wholechip.add(wholechipLabel);
		//Singleton Chip
		JPanel singleton = new JPanel();
		singleton.setLayout(new BoxLayout(singleton, BoxLayout.X_AXIS));
		JLabel singletonLabel = new JLabel("Using Default Singleton Chip");
		JButton singletonButton = new JButton("Use Custom Singleton Chip");
		singletonButton.addActionListener(SingletonChipLocator.getInstance());
		SingletonChipLocator.getInstance().setLabel(singletonLabel);
		singleton.add(singletonButton);
		singleton.add(singletonLabel);
		
		selector.add(GOs);
		selector.add(motifs);
		selector.add(bindingSite);
		selector.add(metabolic);
		selector.add(tff);
		selector.add(wholechip);
		selector.add(singleton);
		menu.add(selector);
	}

	private void makeWholechipPanel(JPanel menu) {
		final JCheckBox wholechipAnalysis = new JCheckBox("Include Whole Chip Analysis");

		JCheckBox wholechipArray = new JCheckBox("Include Whole Chip Array");
		wholechipArray.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Array(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Array(true);
				}
			}
		});
		wholechipArray.setSelected(false);

		final JCheckBox wholechipGO = new JCheckBox("Include Whole Chip GO");
		wholechipGO.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1GO(false);
					wholechipAnalysis.setSelected(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1GO(true);
				}
			}
		});
		wholechipGO.setSelected(false);

		JCheckBox wholechipMetabolic = new JCheckBox("Include Whole Chip Metabolic");
		wholechipMetabolic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Metabolic(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Metabolic(true);
				}
			}
		});
		wholechipMetabolic.setSelected(false);

		JCheckBox wholechipTFF = new JCheckBox("Include Whole Chip TFF");
		wholechipTFF.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1TFF(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1TFF(true);
				}
			}
		});
		wholechipTFF.setSelected(false);

		final JCheckBox wholechipMotif = new JCheckBox("Include Whole Chip Motif");
		wholechipMotif.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Motif(false);
					wholechipAnalysis.setSelected(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Motif(true);
				}
			}
		});
		wholechipMotif.setSelected(false);

		wholechipAnalysis.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Analysis(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Analysis(true);
					wholechipMotif.setSelected(true);
	    			wholechipGO.setSelected(true);
	    		}
			}
	    });
	    wholechipAnalysis.setSelected(false);

	    JPanel wholechip = new JPanel();
	    wholechip.setLayout(new BoxLayout(wholechip, BoxLayout.Y_AXIS));
	    wholechip.add(wholechipArray);
		wholechip.add(wholechipGO);
		wholechip.add(wholechipMetabolic);
		wholechip.add(wholechipTFF);
		wholechip.add(wholechipMotif);
		wholechip.add(wholechipAnalysis);
	    menu.add(wholechip);
	}

	private void makeSingletonPanel(JPanel menu) {
	    final JCheckBox SingletonAnalysis = new JCheckBox("Include Singleton Analysis");

	    JCheckBox SingletonArray = new JCheckBox("Include Singleton Array");
	    SingletonArray.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonArray(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonArray(true);
	    		}
	    	}
	    });
	    SingletonArray.setSelected(true);

	    final JCheckBox SingletonGO = new JCheckBox("Include Singleton GO");
	    SingletonGO.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonGO(false);
	    			SingletonAnalysis.setSelected(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonGO(true);
	    		}
	    	}
	    });
	    SingletonGO.setSelected(true);

	    JCheckBox SingletonMetabolic = new JCheckBox("Include Singleton Metabolic");
	    SingletonMetabolic.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonMetabolic(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonMetabolic(true);
	    		}
	    	}
	    });
	    SingletonMetabolic.setSelected(true);

	    JCheckBox SingletonTFF = new JCheckBox("Include Singleton TFF");
	    SingletonTFF.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonTFF(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonTFF(true);
	    		}
	    	}
	    });
	    SingletonTFF.setSelected(true);

	    final JCheckBox SingletonMotif = new JCheckBox("Include Singleton Motif");
	    SingletonMotif.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonMotif(false);
	    			SingletonAnalysis.setSelected(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonMotif(true);
	    		}
	    	}
	    });
	    SingletonMotif.setSelected(true);

	    SingletonAnalysis.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if (e.getStateChange() == ItemEvent.DESELECTED) {
	    			RunnerChooser.getInstance().setSingletonAnalysis(false);
	    		}
	    		if (e.getStateChange() == ItemEvent.SELECTED) {
	    			RunnerChooser.getInstance().setSingletonAnalysis(true);
	    			SingletonMotif.setSelected(true);
	    			SingletonGO.setSelected(true);
	    		}
	    	}
	    });
	    SingletonAnalysis.setSelected(true);

	    JPanel singletons = new JPanel();
	    singletons.setLayout(new BoxLayout(singletons, BoxLayout.Y_AXIS));
	    singletons.add(SingletonArray);
	    singletons.add(SingletonGO);
	    singletons.add(SingletonMetabolic);
	    singletons.add(SingletonTFF);
	    singletons.add(SingletonMotif);
	    singletons.add(SingletonAnalysis);
	    menu.add(singletons);
	}
}