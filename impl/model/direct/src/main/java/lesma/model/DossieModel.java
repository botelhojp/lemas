package lesma.model;

import jade.core.AID;

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
			myAgent.findReputation(rating.getServer(), rating.getServer());
		}
	}
	
	public List<Rating> getRatings(AID aid) {
		if (aid.equals(myAgent.getAID())){
			return dossie;
		}else{
			return null;
		}
	}
	
	public Boolean test(Rating rating) {
		
		return (Math.random() <= 0.2);
	}
}
