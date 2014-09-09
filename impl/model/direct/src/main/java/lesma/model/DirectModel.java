package lesma.model;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Direct Model")
public class DirectModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}
	
	public void findReputation(AID server) {
		myAgent.sendMessage(myAgent.getAID(), ACLMessage.REQUEST, "GET_DOSSIE", "");
	}


}
