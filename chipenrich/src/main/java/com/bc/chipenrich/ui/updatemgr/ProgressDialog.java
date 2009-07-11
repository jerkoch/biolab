package com.bc.chipenrich.ui.updatemgr;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JWindow {
   private JLabel messageLabel;
   private JProgressBar progressBar;

   ProgressDialog(JFrame owner, int min, int max, String msg) {
      super(owner);

      Point p = new Point(owner.getLocation());
      p.move((int) owner.getSize().getWidth() / 2,
            (int) owner.getSize().getHeight() / 2);
      setLocation(p);

      progressBar = new JProgressBar(min, max);
      messageLabel = new JLabel(msg);
      messageLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.setLayout(new BorderLayout());
      panel.add(progressBar, BorderLayout.CENTER);
      panel.add(messageLabel, BorderLayout.SOUTH);
      getContentPane().add(panel);
      pack();
   }

   public void setMessage(final String string) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            messageLabel.setText(string);
         }
      });
   }

   public void setProgress(final int progress) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            progressBar.setValue(progress);
         }
      });
   }
}