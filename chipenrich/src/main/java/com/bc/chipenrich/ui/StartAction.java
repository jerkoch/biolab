/**
 * 
 */
package com.bc.chipenrich.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bc.chipenrich.service.ChipEnrichService;
import com.bc.chipenrich.service.ChipEnrichServiceImpl;
import com.bc.chipenrich.ui.runner.Ath1ChipRunner;
import com.bc.chipenrich.ui.runner.SingletonChipRunner;

/**
 * @author Jeremy Koch
 */
public class StartAction extends AbstractAction {

   private ChipEnrich parent;

   private ChipEnrichService ces = new ChipEnrichServiceImpl();

   public StartAction(ChipEnrich parent) {
      this.parent = parent;
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    */
   public void actionPerformed(ActionEvent event) {

      new Thread(new Runnable() {
         public void run() {
            // open the file chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Query Files");
            chooser.setMultiSelectionEnabled(true);
            chooser.setApproveButtonText("Select");

            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
               return;
            }

            // create and display the dialog
            ProgressDialog progressDialog = new ProgressDialog();
            progressDialog.setVisible(true);

            // setup listener for close
            progressDialog.addWindowListener(new WindowAdapter() {
               public void windowClosing(WindowEvent aEvent) {
               }
            });


            // start the first ath1 run...
            try {
               Thread thread = new Ath1ChipRunner(progressDialog.getStatusLabel(), ces, chooser.getSelectedFiles(),
                     chooser.getCurrentDirectory().getAbsolutePath());
               thread.start();
               thread.join();
            } catch (Exception e) {
            }

            // start the second singletons run...
            try {
               Thread thread = new SingletonChipRunner(progressDialog.getStatusLabel(), ces,
                     chooser.getSelectedFiles(), chooser.getCurrentDirectory().getAbsolutePath());
               thread.start();
               thread.join();
            } catch (Exception e) {
            }

            // kill the dialog
            progressDialog.dispose();

            // show the completion message box
            JOptionPane.showMessageDialog(parent, "Done!\n\nFiles were saved in: "
                  + chooser.getCurrentDirectory());
         }
      }).start();
   }

   private class ProgressDialog extends JDialog {

      private JLabel messageLabel = new JLabel();

      public ProgressDialog() {
         super(parent);
         setTitle("Running...");
         JPanel panel = new JPanel();
         panel.setBorder(BorderFactory.createEtchedBorder());
         panel.setLayout(new BorderLayout());

         messageLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
         panel.add(messageLabel, BorderLayout.CENTER);
         getContentPane().add(panel);
         setSize(300, 100);
      }

      public JLabel getStatusLabel() {
         return messageLabel;
      }
   }
}
