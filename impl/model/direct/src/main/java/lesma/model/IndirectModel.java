package lesma.model;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;
import openjade.ontology.RequestRating;

@TrustModel(name = "Indirect Model")
public class IndirectModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private static final String NEIGHBORS = "NEIGHBORS";
	private static final String FREQUENCY = "FREQUENCY";

	public IndirectModel() {
		properties.put(NEIGHBORS, "0.1");
		properties.put(FREQUENCY, "3");
	}

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}

	public void findReputation(AID server) {
		RequestRating rr = new RequestRating();
		rr.setAid(server);
		myAgent.sendMessage(new AID("lemas_loader", false), ACLMessage.REQUEST, "GET_INDIRECT", rr);
	}
}
