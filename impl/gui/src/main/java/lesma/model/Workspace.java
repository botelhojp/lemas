package lesma.model;

import java.io.File;

public class Workspace {
	
	private static Workspace instance = new Workspace();
	
	public static final String LESMA_HOME = System.getProperty("user.home") + File.separator +  ".lesma";
	public static final String FOLDER_TRUSTMODEL = LESMA_HOME + File.separator +  "trusmodel";
	public static final String FOLDER_PROJECT = LESMA_HOME + File.separator +  "projects";
	
	private Workspace(){}
	
	public static Workspace getIntance(){
		return instance;
	}

	public void initialize() {
		File fileworkspace = new File(LESMA_HOME);
		if (!fileworkspace.exists()){
			fileworkspace.mkdirs();
			new File(FOLDER_TRUSTMODEL).mkdirs();
			new File(FOLDER_PROJECT).mkdirs();
		}
	}

}
