package lemas.model;

import lemas.form.FrameMain;
import lemas.util.Data;
import lemas.util.Message;

public class LemasLog {

    private static boolean enabled = true;

    public static void info(String msg) {
        if (enabled) {
        	Message.message(msg);
        }
    }

    public static void debug(String msg) {
        if (enabled) {
            info(msg);
        }
    }

    public static void erro(String msg) {
        if (enabled) {
            info(msg);
        }
    }

    public static void erro(Throwable e) {
        if (enabled) {
            String msg = Data.exceptionToStr(e);
            System.err.println(msg);
            Message.message(msg);
        }
    }

    public static void clean() {
    	FrameMain.getInstance().clean();
    }

    public static void setEnable(boolean value) {
        enabled = value;
    }

}
