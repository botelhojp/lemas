package lesma.model;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.trust.Reliable;

@TrustModel(name = "Ramdom Model")
public class RamdomModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public Reliable isReliable(AID agent) {
		double r = Math.random();
		if (r >= 0.7001)
			return Reliable.YES;
		if (r >= 0.3001)
			return Reliable.NO;
		return Reliable.UNCERTAIN;
	}

}
