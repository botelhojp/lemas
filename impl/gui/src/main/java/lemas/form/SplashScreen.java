/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lemas.form;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import lemas.Lemas;


public class SplashScreen extends JWindow {

    private static final long serialVersionUID = 1L;
    private BorderLayout borderLayout = new BorderLayout();
    private JLabel imageLabel = new JLabel();
    private JProgressBar progressBar = new JProgressBar(0, 100);

    public SplashScreen() {
        ImageIcon imageIcon = new ImageIcon(Lemas.class.getResource("/tesmalogo.png"));
        imageLabel.setIcon(imageIcon);
        setLayout(borderLayout);
        add(imageLabel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    public void showScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }

    public void close() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        });
    }

    public void setProgress(final String message, final int progress) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progressBar.setValue(progress);
                if (message == null) {
                    progressBar.setStringPainted(false);
                } else {
                    progressBar.setStringPainted(true);
                }
                progressBar.setString("Carregar " + message + "...");
            }
        });
    }
}