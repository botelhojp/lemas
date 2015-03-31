package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Central Model")
public class CentralModel extends AbstractModel {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		if (isIamClient(rating) || !direct) {
			if (data.containsKey(rating.getServer())) {
				data.get(rating.getServer()).addRating(rating);
			} else {
				TrustModelData tmd = new TrustModelData();
				tmd.addRating(rating);
				data.put(rating.getServer(), tmd);
			}
			myAgent.test(rating.getServer());
		}
	}

	
	@Override
	public String test(AID aid) {
		return data.get(aid).getTest(test);
	}

}
