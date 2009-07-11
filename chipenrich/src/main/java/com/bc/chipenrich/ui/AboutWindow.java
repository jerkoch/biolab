package com.bc.chipenrich.ui;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class AboutWindow extends JDialog {

   private JEditorPane aboutPane;

   public AboutWindow(JFrame owner) {
      super(owner, "About Chip Enrich");

      getContentPane().setLayout(new BorderLayout());
      setModal(true);
      Point p = new Point(owner.getLocation());
      p.move((int) owner.getSize().getWidth() / 3,
            (int) owner.getSize().getHeight() / 3);
      setLocation(p);
      setSize(300, 150);

      aboutPane = new JEditorPane();
      aboutPane.setContentType("text/html");
      aboutPane.setBackground(owner.getBackground());
      aboutPane.setEditable(false);
      
      try {
         aboutPane.setPage(getClass().getClassLoader().getResource(
               getClass().getPackage().getName().replace('.', '/') + "/about.html"));
      } catch (Exception e) {
      }

      getContentPane().add(aboutPane, BorderLayout.CENTER);
   }
}
