package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Regret Model")
public class RegretModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		super.addRating(rating, direct);
		findWitnesses(rating.getServer());
		findReputation(rating.getServer());
	}
	
	private void findWitnesses(AID server) {
		myAgent.findWitnesses(server);		
	}

	public void findReputation(AID server) {
//		for (AID witness : getWitnesses()) {
//			myAgent.findReputation(witness, server);
//		}
	}
}
