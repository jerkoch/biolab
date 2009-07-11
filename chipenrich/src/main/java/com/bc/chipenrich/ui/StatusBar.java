package com.bc.chipenrich.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StatusBar extends JPanel {

   private JLabel versionPanel = new JLabel("", SwingConstants.CENTER);
   private JPanel msgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
   private JLabel curLinePanel = new JLabel("0", SwingConstants.CENTER);
   private JLabel totalLinePanel = new JLabel("0", SwingConstants.CENTER);
   private GridBagConstraints c;

   public StatusBar(ChipEnrich parent) {

      setLayout(new GridBagLayout());

      msgPanel.setBorder(BorderFactory.createEtchedBorder());
      msgPanel.add(new JLabel("Chip Enrich"));

      c = new GridBagConstraints();
      c.insets = new Insets(2, 2, 2, 2);
      c.ipadx = 2;
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.WEST;

      c.gridx = 0;
      c.weightx = 0.0;
      versionPanel.setBorder(BorderFactory.createEtchedBorder());
      versionPanel.setPreferredSize(new Dimension(50, 20));
      versionPanel.setText(parent.getVersion());
      add(versionPanel, c);

      c.gridx = 1;
      c.weightx = 5.0;
      add(msgPanel, c);

      c.gridx = 3;
      c.weightx = 0.0;
      curLinePanel.setPreferredSize(new Dimension(60, 20));
      curLinePanel.setBorder(BorderFactory.createEtchedBorder());
      add(curLinePanel, c);

      c.gridx = 4;
      c.weightx = 0.0;
      totalLinePanel.setPreferredSize(new Dimension(60, 20));
      totalLinePanel.setBorder(BorderFactory.createEtchedBorder());
      add(totalLinePanel, c);
   }
}
