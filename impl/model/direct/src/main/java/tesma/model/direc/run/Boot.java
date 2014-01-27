package tesma.model.direc.run;

import openjade.core.annotation.TrustModel;

import org.apache.log4j.Logger;

/**
 * Classe inicializadora
 * @author vanderson
 *
 */
@TrustModel(name="Direct Model")
public class Boot {
	
	protected static Logger log = Logger.getLogger(Boot.class);

	public static void main(String[] args) {
		try {
			if (args != null && args.length > 0) {
				openjade.Boot.main(args);
			} else {
				openjade.Boot.loadXml();
			}			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
}
