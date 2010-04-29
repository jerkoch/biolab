package com.bc.chipenrich.ui;

import com.bc.chipenrich.ui.chooser.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainWindow extends JPanel {
	public MainWindow() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		makeMenus();
	}

	private void makeMenus() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		makePlantPanel(topPanel);
		makeSelectorPanel(topPanel);

		this.add(topPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		makeATH1Panel(bottomPanel);
		makeSingletonPanel(bottomPanel);
		this.add(bottomPanel);
	}

	private void makePlantPanel(JPanel menu) {
		JPanel plantChooser = new JPanel();
		plantChooser.setLayout(new BoxLayout(plantChooser, BoxLayout.Y_AXIS));

		JRadioButton arabidopsisButton = new JRadioButton("Arabidopsis");
		arabidopsisButton.setActionCommand("arabidopsis");
		arabidopsisButton.addActionListener(PlantChooser.getInstance());
		arabidopsisButton.setSelected(true);
		JRadioButton soybeanButton = new JRadioButton("Soybean");
		soybeanButton.setActionCommand("soybean");
		soybeanButton.addActionListener(PlantChooser.getInstance());
		
		ButtonGroup plants = new ButtonGroup();
		plants.add(arabidopsisButton);
		plants.add(soybeanButton);

		plantChooser.add(arabidopsisButton);
		plantChooser.add(soybeanButton);

		menu.add(plantChooser);
	}

	private void makeSelectorPanel(JPanel menu) {
		JPanel selector = new JPanel();
		selector.setLayout(new BoxLayout(selector, BoxLayout.Y_AXIS));
		//GOs
		JPanel GOs = new JPanel();
		GOs.setLayout(new BoxLayout(GOs, BoxLayout.X_AXIS));
		JLabel GOLabel = new JLabel("Using Default GOs");
		JButton GOButton = new JButton("Use Custom GO Files");
		GOButton.addActionListener(new GOChooser(GOLabel));
		GOs.add(GOButton);
		GOs.add(GOLabel);
		//Motif
		JPanel motifs = new JPanel();
		motifs.setLayout(new BoxLayout(motifs, BoxLayout.X_AXIS));
		JLabel motifLabel = new JLabel("Using Default Motifs");
		JButton motifButton = new JButton("Use Custom Motif Files");
		motifButton.addActionListener(new MotifChooser(motifLabel));
		motifs.add(motifButton);
		motifs.add(motifLabel);
		//FASTA if needed
		//JFileChooser
		//JButton
		//JLabel
		selector.add(GOs);
		selector.add(motifs);
		menu.add(selector);
	}

	private void makeATH1Panel(JPanel menu) {
		final JCheckBox ATH1Analysis = new JCheckBox("Include ATH1 Analysis");

		JCheckBox ATH1Array = new JCheckBox("Include ATH1 Array");
		ATH1Array.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Array(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Array(true);
				}
			}
		});
		ATH1Array.setSelected(false);

		final JCheckBox ATH1GO = new JCheckBox("Include ATH1 GO");
		ATH1GO.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1GO(false);
					ATH1Analysis.setSelected(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1GO(true);
				}
			}
		});
		ATH1GO.setSelected(false);

		JCheckBox ATH1Metabolic = new JCheckBox("Include ATH1 Metabolic");
		ATH1Metabolic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Metabolic(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Metabolic(true);
				}
			}
		});
		ATH1Metabolic.setSelected(false);

		JCheckBox ATH1TFF = new JCheckBox("Include ATH1 TFF");
		ATH1TFF.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1TFF(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1TFF(true);
				}
			}
		});
		ATH1TFF.setSelected(false);

		final JCheckBox ATH1Motif = new JCheckBox("Include ATH1 Motif");
		ATH1Motif.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Motif(false);
					ATH1Analysis.setSelected(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Motif(true);
				}
			}
		});
		ATH1Motif.setSelected(false);

		ATH1Analysis.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					RunnerChooser.getInstance().setATH1Analysis(false);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					RunnerChooser.getInstance().setATH1Analysis(true);
					ATH1Motif.setSelected(true);
	    			ATH1GO.setSelected(true);
	    		}
			}
	    });
	    ATH1Analysis.setSelected(false);

	    JPanel ATH1 = new JPanel();
	    ATH1.setLayout(new BoxLayout(ATH1, BoxLayout.Y_AXIS));
	    ATH1.add(ATH1Array);
		ATH1.add(ATH1GO);
		ATH1.add(ATH1Metabolic);
		ATH1.add(ATH1TFF);
		ATH1.add(ATH1Motif);
		ATH1.add(ATH1Analysis);
	    menu.add(ATH1);
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