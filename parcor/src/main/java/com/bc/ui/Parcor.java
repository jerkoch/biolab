package com.bc.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Parcor extends JFrame {
	public Parcor() {
		super();
		
		setTitle("Partial Correlation");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent aEvent) {
				System.exit(0);
			}
		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new MainWindow(), BorderLayout.CENTER);

		setSize(600, 300);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Parcor();
	}
}

