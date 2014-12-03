package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Central Model")
public class CentralModel extends AbstractModel {
	
	protected static TrustModelData unoData = new TrustModelData();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		if (isIamClient(rating)){
			unoData.addRating(rating);
			myAgent.testLastRating(rating.getServer());	
		}
	}
	
	@Override
	public Boolean test(AID aid) {
		return unoData.getTest();
	}

}
