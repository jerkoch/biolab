package com.bc.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bc.path.Correlation;
import com.bc.path.CorrelationFactory;
import com.bc.path.Node;
import com.bc.ui.locator.AffyATH1Locator;
import com.bc.ui.locator.ExpressionMapLocator;
import com.bc.ui.locator.PathwayLocator;

public class MainWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea status;
	private JButton startParcor;
	private JButton customAffy;
	private JButton customExpMap;
	private JButton customPathway;

	public MainWindow() {
		makeLayout();
	}

	private void makeLayout() {
		this.setLayout(new GridBagLayout());

		RunListener r = new RunListener(this);

		startParcor = new JButton("Start Partial Correlation");
		startParcor.addActionListener(r);

		JLabel affyLabel = new JLabel("Using default Affy / ATH1");
		customAffy = new JButton("Use custom Affy / ATH1");
		customAffy.addActionListener(AffyATH1Locator.getInstance());
		AffyATH1Locator.getInstance().setLabel(affyLabel);

		JLabel expMapLabel = new JLabel("Using default Expression Map");
		customExpMap = new JButton("Use custom Expression Map");
		customExpMap.addActionListener(ExpressionMapLocator.getInstance());
		ExpressionMapLocator.getInstance().setLabel(expMapLabel);

		JLabel pathwayLabel = new JLabel("Using default Pathways");
		customPathway = new JButton("Use custom Pathway");
		customPathway.addActionListener(PathwayLocator.getInstance());
		PathwayLocator.getInstance().setLabel(pathwayLabel);

		Correlation[] correlations = CorrelationFactory.getAvailableCorrelations();
		Correlation defaultCorrelation = correlations[0];
		Node.setCorrelation(defaultCorrelation);

		JComboBox correlation = new JComboBox(correlations);
		correlation.setSelectedItem(defaultCorrelation);
		correlation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Node.setCorrelation((Correlation) cb.getSelectedItem());
			}
		});

		status = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(status);
		status.setEditable(false);

		// Add all components
		GridBagConstraints startC = new GridBagConstraints();
		startC.gridx = 0;
		startC.gridy = 0;
		startC.gridwidth = GridBagConstraints.REMAINDER;
		startC.anchor = GridBagConstraints.CENTER;
		this.add(startParcor, startC);

		GridBagConstraints affyC = new GridBagConstraints();
		affyC.gridx = 0;
		affyC.gridy = 1;
		affyC.fill = GridBagConstraints.HORIZONTAL;
		this.add(customAffy, affyC);

		GridBagConstraints affyLabelC = new GridBagConstraints();
		affyLabelC.gridx = 1;
		affyLabelC.gridy = 1;
		affyLabelC.anchor = GridBagConstraints.LINE_START;
		this.add(affyLabel, affyLabelC);

		GridBagConstraints expMapC = new GridBagConstraints();
		expMapC.gridx = 0;
		expMapC.gridy = 2;
		expMapC.fill = GridBagConstraints.HORIZONTAL;
		this.add(customExpMap, expMapC);

		GridBagConstraints expMapLabelC = new GridBagConstraints();
		expMapLabelC.gridx = 1;
		expMapLabelC.gridy = 2;
		expMapLabelC.anchor = GridBagConstraints.LINE_START;
		this.add(expMapLabel, expMapLabelC);

		GridBagConstraints pathwayC = new GridBagConstraints();
		pathwayC.gridx = 0;
		pathwayC.gridy = 3;
		pathwayC.fill = GridBagConstraints.HORIZONTAL;
		this.add(customPathway, pathwayC);

		GridBagConstraints pathwayLabelC = new GridBagConstraints();
		pathwayLabelC.gridx = 1;
		pathwayLabelC.gridy = 3;
		pathwayLabelC.anchor = GridBagConstraints.LINE_START;
		this.add(pathwayLabel, pathwayLabelC);
		
		GridBagConstraints correlationC = new GridBagConstraints();
		correlationC.gridx = 0;
		correlationC.gridy = 4;
		correlationC.fill = GridBagConstraints.HORIZONTAL;
		this.add(correlation, correlationC);

		GridBagConstraints correlationLabelC = new GridBagConstraints();
		correlationLabelC.gridx = 1;
		correlationLabelC.gridy = 4;
		correlationLabelC.anchor = GridBagConstraints.LINE_START;
		this.add(new JLabel("Correlation Type", JLabel.RIGHT), correlationLabelC);

		GridBagConstraints statusC = new GridBagConstraints();
		statusC.gridx = 0;
		statusC.gridy = 5;
		statusC.weightx = 1;
		statusC.weighty = 1;
		statusC.gridwidth = 2;
		statusC.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, statusC);
	}

	public void enableButtons(boolean value) {
		startParcor.setEnabled(value);
		customAffy.setEnabled(value);
		customExpMap.setEnabled(value);
		customPathway.setEnabled(value);
	}

	public void printMessage(String message) {
		status.append(message);
		status.setCaretPosition(status.getDocument().getLength());
	}
}