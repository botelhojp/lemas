package lemas;

import java.io.File;

import javax.swing.SwingUtilities;

import lemas.form.FrameMain;
import lemas.trust.metrics.IMetrics;

public class Lemas {

	public static String file1 = null;
	public static IMetrics metrics = null;

	public static void main(String args[]) {
		if (args.length == 1) {
			file1 = args[0];
			if (!(new File(file1).exists())) {
				file1 = System.getProperty("user.home") + file1;
				if (!(new File(file1).exists())) {
					file1 = args[0];
				}
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FrameMain.getInstance();
			}
		});
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void seIMetrics(IMetrics abstractIMetric) {
		metrics = abstractIMetric;
	}
}
