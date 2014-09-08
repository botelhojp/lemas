package lemas;

import java.io.File;

import lemas.form.FrameMain;
import lemas.form.SplashScreen;
import lemas.model.Workspace;
import lesma.model.Constants;

import org.apache.log4j.Logger;


public class Lemas {

	private static Logger log = Logger.getLogger(Lemas.class);

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
			spaScreen.setProgress("limpeza de arquivos antigos", 30);
			cleanFiles();
			spaScreen.setProgress("openjade", 70);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Erro ao inicar o lesma. \nCausa:", ex);
		} finally {
			spaScreen.close();
		}
	}

	public static void cleanFiles() {
		File folderTmp = new File(System.getProperty("java.io.tmpdir"));
		File[] listOfFiles = folderTmp.listFiles();
		for (File file : listOfFiles) {
			if (file.getName().endsWith(Constants.AGENT_FILE_EXTENSION)){
				file.delete();
			}
		}
	}
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
