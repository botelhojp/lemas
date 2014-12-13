package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Direct Model")
public class DirectModel extends AbstractModel {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		super.addRating(rating, direct);
		if (isIamClient(rating)){
			myAgent.test(rating.getServer());	
		}
	}

	public void findReputation(AID server) {
//		myAgent.sendMessage(myAgent.getAID(), ACLMessage.REQUEST, "GET_DOSSIE", "");
	}


}



