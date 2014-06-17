package lemas.model;

import lemas.form.FrameMain;
import lemas.util.Data;

public class LemasLog {

	public static void info(String msg) {
		System.out.println(msg);
		FrameMain.getInstance().message(msg);
	}

	public static void debug(String msg) {
		info(msg);
	}

	public static void erro(String msg) {
		info(msg);
	}

	public static void erro(Throwable e) {
		String msg = Data.exceptionToStr(e);
		System.err.println(msg);
		FrameMain.getInstance().message(msg);
	}

	public static void clean() {
		FrameMain.getInstance().clean();		
	}

}
