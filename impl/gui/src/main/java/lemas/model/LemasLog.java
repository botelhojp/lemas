package lemas.model;

import lemas.form.FrameMain;

public class LemasLog {
		
	public static void info(String msg){
		FrameMain.getInstance().message(msg);
	}

	
	public static void debug(String msg){
		info(msg);
	}
	
	public static void erro(String msg){
		info(msg);
	}

	
}
