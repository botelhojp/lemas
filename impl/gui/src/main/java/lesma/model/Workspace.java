package lesma.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class Workspace {

	private static final Workspace instance = new Workspace();

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
	public static final String LESMA_HOME = System.getProperty("user.home") + File.separator + ".lesma";
	public static final String FOLDER_TRUSTMODEL = LESMA_HOME + File.separator + "trusmodel";
	public static final String FOLDER_PROJECT = LESMA_HOME + File.separator + "projects";

	private Workspace() {
	}

	public static Workspace getIntance() {
		return instance;
	}

	public void initialize() {
		mkdir(LESMA_HOME);
		mkdir(FOLDER_TRUSTMODEL);
		mkdir(FOLDER_PROJECT);
	}

	public void mkdir(String file) {
		File fileworkspace = new File(file);
		if (!fileworkspace.exists()) {
			fileworkspace.mkdirs();
		}
	}
}
