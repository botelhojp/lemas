package lemas;

import java.io.File;

import lemas.db.LemasDB;
import lemas.form.FrameMain;
import lemas.form.SplashScreen;
import lemas.model.Workspace;
import lemas.trust.Constants;
import lemas.trust.metrics.IMetrics;

import org.apache.log4j.Logger;


public class Lemas {

	public static String file1 = null;
	public static String file2 = null;
	public static IMetrics metrics = null;
	
	private static Logger log = Logger.getLogger(Lemas.class);

	public static void main(String args[]) {
		if (args.length==2){			
			file1=args[0];
			file2=args[1];			
			if (!(new File(file1).exists())){
				file1 =  System.getProperty("user.home") + file1;
				if (!(new File(file1).exists())){
					file1=args[0];
				}
			}
			if (!(new File(file2).exists())){
				file2 =  System.getProperty("user.home") + file2;
				if (!(new File(file2).exists())){
					file2=args[1];	
				}
			}
		}
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
		LemasDB db = new LemasDB();
		db.connect();
		db.cleanAll();
		db.close();
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

	public static void saveCSV() {
		if (metrics != null){
			File file = new File(System.getProperty("user.home") + File.separatorChar + "Dropbox" + File.separatorChar + "result.csv");
			metrics.save(file);
		}
		
	}

	public static void seIMetrics(IMetrics abstractIMetric) {
		metrics = abstractIMetric;		
	}
}
