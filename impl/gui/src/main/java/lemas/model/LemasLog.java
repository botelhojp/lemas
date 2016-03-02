package lemas.model;

import lemas.form.FrameMain;
import lemas.util.Data;

public class LemasLog {

    private static boolean enabled = true;

    public static void info(String msg) {
        if (enabled) {
            FrameMain.getInstance().message(msg);
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
            FrameMain.getInstance().message(msg);
        }
    }

    public static void clean() {
    	FrameMain.getInstance().clean();
    }

    public static void setEnable(boolean value) {
        enabled = value;
    }

}
