package lemas.util;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;

public class CommonsFrame {
	
	 public static void loadFrame(JInternalFrame window, JDialog frame) {             
	        javax.swing.GroupLayout windowLayout = new javax.swing.GroupLayout(window.getContentPane());
	        window.getContentPane().setLayout(windowLayout);        
	        windowLayout.setHorizontalGroup(
	            windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, frame.getWidth(), Short.MAX_VALUE)
	        );
	        windowLayout.setVerticalGroup(
	            windowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, frame.getHeight(), Short.MAX_VALUE)
	        );
	        window.getContentPane().removeAll();
	        window.getContentPane().add(frame.getContentPane());        
	        window.setTitle(frame.getTitle());
	        window.setVisible(true);
	    }

}
