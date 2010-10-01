package com.bc.ui;

import javax.swing.JOptionPane;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.bc.path.Runner;

public class RunListener implements ActionListener {
	private MainWindow parent;
	
	public RunListener(MainWindow m) {
		this.parent = m;
	}
	
	public void actionPerformed(ActionEvent e) {
		String output = JOptionPane.showInputDialog(parent, "Where will you save the output?");
		if (output == null || output.equals("")) {
			return;
		}
		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		parent.enableButtons(false);
		Runner.run(output, parent);
		parent.enableButtons(true);
		parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}