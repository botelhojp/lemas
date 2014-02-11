package lesma.model.direct.run;

import lesma.annotations.StartSMA;
import lesma.model.direct.agent.TaskAgent;

import org.apache.log4j.Logger;

@StartSMA(
		trustmodel = "Direct Model - d002", 
		agents={TaskAgent.class}
		)
public class Direct002 {

	protected static Logger log = Logger.getLogger(Direct002.class);

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
