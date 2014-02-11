package lesma;

import lesma.form.FrameMain;
import lesma.form.SplashScreen;
import lesma.model.Workspace;

import org.apache.log4j.Logger;


public class Lesma {

	private static Logger log = Logger.getLogger(Lesma.class);

	public static void main(String args[]) {
		SplashScreen spaScreen = new SplashScreen();
		try {
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					FrameMain.getInstance().setVisible(true);
				}
			});
			spaScreen.showScreen();
			spaScreen.setProgress("workspace", 0);
			Workspace.getIntance().initialize();
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
