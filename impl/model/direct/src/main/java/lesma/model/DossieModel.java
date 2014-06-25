package lesma.model;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Dossie Model")
public class DossieModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
		addWitness(rating.getServer());
		findReputation(rating.getServer());
	}
	
	

	private void findReputation(AID server) {
		for (AID witness : getWitnesses()) {
			myAgent.findReputation(witness, server);
		}
	}
}
