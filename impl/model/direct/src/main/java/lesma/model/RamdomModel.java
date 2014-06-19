package lesma.model;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.trust.Reliable;

@TrustModel(name = "Ramdom Model")
public class RamdomModel extends AbstractModel {
	
	public RamdomModel() {
		properties.put("yes", "0.7001");
		properties.put("no", "0.3001");
	}

	private static final long serialVersionUID = 1L;

	public Reliable isReliable(AID agent) {
		double r = Math.random();
		if (r >= getDouble("yes"))
			return Reliable.YES;
		if (r >= getDouble("no"))
			return Reliable.NO;
		return Reliable.UNCERTAIN;
	}
}
