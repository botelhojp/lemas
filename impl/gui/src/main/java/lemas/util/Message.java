package lemas.util;

import lemas.form.FrameMain;

public class Message {
	public static void message(String message){
		FrameMain.getInstance().message(message);		
	}

	public static void message(Throwable e) {
		message(StackTraceUtil.getStackTrace(e));
	}

	public static void message(String message, Throwable e) {
		message(message + " --- " + StackTraceUtil.getStackTrace(e));
	}
}
