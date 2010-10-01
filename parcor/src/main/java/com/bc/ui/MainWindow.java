package com.bc.ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import com.bc.ui.locator.AffyATH1Locator;
import com.bc.ui.locator.ExpressionMapLocator;
import com.bc.ui.locator.PathwayLocator;

public class MainWindow extends JPanel {
	private JTextArea status;
	private JButton startParcor;
	private JButton customAffy;
	private JButton customExpMap;
	private JButton customPathway;

	public MainWindow() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		RunListener r = new RunListener(this);

		startParcor = new JButton("Start Partial Correlation");
		startParcor.addActionListener(r);

		JPanel affyPanel = new JPanel();
		affyPanel.setLayout(new BoxLayout(affyPanel, BoxLayout.LINE_AXIS));
		JLabel affyLabel = new JLabel("Using default Affy / ATH1");
		customAffy = new JButton("Use custom Affy / ATH1");
		customAffy.addActionListener(AffyATH1Locator.getInstance());
		AffyATH1Locator.getInstance().setLabel(affyLabel);
		affyPanel.add(customAffy);
		affyPanel.add(affyLabel);

		JPanel expMapPanel = new JPanel();
		expMapPanel.setLayout(new BoxLayout(expMapPanel, BoxLayout.LINE_AXIS));
		JLabel expMapLabel = new JLabel("Using default Expression Map");
		customExpMap = new JButton("Use custom Expression Map");
		customExpMap.addActionListener(ExpressionMapLocator.getInstance());
		ExpressionMapLocator.getInstance().setLabel(expMapLabel);
		expMapPanel.add(customExpMap);
		expMapPanel.add(expMapLabel);

		JPanel pathwayPanel = new JPanel();
		pathwayPanel.setLayout(new BoxLayout(pathwayPanel, BoxLayout.LINE_AXIS));
		JLabel pathwayLabel = new JLabel("Using default Pathways");
		customPathway = new JButton("Use custom Pathway");
		customPathway.addActionListener(PathwayLocator.getInstance());
		PathwayLocator.getInstance().setLabel(pathwayLabel);
		pathwayPanel.add(customPathway);
		pathwayPanel.add(pathwayLabel);

		status = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(status); 
		status.setEditable(false);

        //Add all components
        this.add(startParcor);
		this.add(affyPanel);
		this.add(expMapPanel);
		this.add(pathwayPanel);
		this.add(scrollPane);
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