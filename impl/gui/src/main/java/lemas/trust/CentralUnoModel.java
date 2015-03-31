package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Central Uno Model")
public class CentralUnoModel extends AbstractModel {
	
	protected static TrustModelData unoData = new TrustModelData();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		if (isIamClient(rating)){
			unoData.addRating(rating);
			myAgent.test(rating.getServer());	
		}
	}
	
	@Override
	public String test(AID aid) {
		return unoData.getTest(test);
	}

}
