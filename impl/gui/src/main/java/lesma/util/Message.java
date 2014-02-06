package lesma.util;

import javax.swing.JOptionPane;

public class Message {
	public static void error(String message){
		JOptionPane.showMessageDialog(null, message,  "Error",   JOptionPane.ERROR_MESSAGE);
	}
		
}
