package lesma.model;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Dossie Model")
public class DossieModel extends AbstractModel {
	
	List<Rating> dossie = new ArrayList<Rating>();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		if (rating.getServer().equals(myAgent.getAID())) {
			dossie.add(rating);
		} else {
			super.addRating(rating);
		}
	}
	
	public List<Rating> getRatings() {
		return dossie;
	}
	
	public void findReputation(AID server) {
		myAgent.sendMessage(server, ACLMessage.REQUEST, "GET_DOSSIE", "");
	}

	
}
