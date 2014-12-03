package lemas.trust;

import java.util.HashMap;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Central Model")
public class CentralModel extends AbstractModel {
	
	protected static HashMap<AID, TrustModelData> mydata = new HashMap<AID, TrustModelData>();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		if (isIamClient(rating) || !direct) {
			if (mydata.containsKey(rating.getServer())) {
				mydata.get(rating.getServer()).addRating(rating);
			} else {
				TrustModelData tmd = new TrustModelData();
				tmd.addRating(rating);
				mydata.put(rating.getServer(), tmd);
			}
			myAgent.testLastRating(rating.getServer());
		}
	}

	
	@Override
	public Boolean test(AID aid) {
		return mydata.get(aid).getTest();
	}

}
