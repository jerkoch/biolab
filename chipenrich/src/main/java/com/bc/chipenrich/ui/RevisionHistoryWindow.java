package com.bc.chipenrich.ui;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class RevisionHistoryWindow extends JDialog {

   private JEditorPane revisionPane;

   public RevisionHistoryWindow(JFrame owner) {
      super(owner, "Chip Enrich Revision History");

      getContentPane().setLayout(new BorderLayout());
      setModal(true);
      Point p = new Point(owner.getLocation());
      p.move((int) owner.getSize().getWidth() / 8,
            (int) owner.getSize().getHeight() / 4);
      setLocation(p);
      setSize(800, 400);

      revisionPane = new JEditorPane();
      revisionPane.setContentType("text/plain");
      revisionPane.setBackground(owner.getBackground());
      revisionPane.setEditable(false);
      
      try {
         revisionPane.setPage(getClass().getClassLoader().getResource("revision_history.txt"));
      } catch (Exception e) {
      }

      getContentPane().add(new JScrollPane(revisionPane), BorderLayout.CENTER);
   }
}
