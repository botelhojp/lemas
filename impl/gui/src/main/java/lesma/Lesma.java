package lesma;

import lesma.annotations.StartSMA;
import lesma.form.SplashScreen;

import org.apache.log4j.Logger;


@StartSMA(trustmodel="oba")
public class Lesma {

	private static Logger log = Logger.getLogger(Lesma.class);

	public static void main(String args[]) {
		SplashScreen spaScreen = new SplashScreen();
		try {
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new lesma.form.FrameMain().setVisible(true);
				}
			});
			spaScreen.showScreen();
			spaScreen.setProgress("interface", 0);
			delay(500);
			spaScreen.setProgress("agents", 30);
			delay(500);
			spaScreen.setProgress("openjade", 70);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Erro ao inicar o lesma. \nCausa:", ex);
		} finally {
			spaScreen.close();
		}
	}

	private static void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
