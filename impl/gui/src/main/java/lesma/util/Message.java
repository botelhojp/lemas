package lesma.util;

import java.awt.Component;
import javax.swing.JOptionPane;

public class Message {
	public static void error(String message, Component c){
		JOptionPane.showMessageDialog(null, message,  "Error",   JOptionPane.ERROR_MESSAGE);
	}

    public static void info(String message, Component c) {
        JOptionPane.showMessageDialog(c, message,  "Info",   JOptionPane.INFORMATION_MESSAGE);
    }
		
}
