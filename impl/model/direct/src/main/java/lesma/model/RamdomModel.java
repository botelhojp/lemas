package lesma.model;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.trust.Reliable;

@TrustModel(name = "Ramdom Model")
public class RamdomModel extends AbstractModel {
	
	public RamdomModel() {
		properties.clear();
		properties.put("RELIABLE", "0.8");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Reliable isReliable(AID agent) {
		double r = Math.random();
		if (r <= getDouble("RELIABLE"))
			return Reliable.YES;
		return Reliable.NO;
	}
}
