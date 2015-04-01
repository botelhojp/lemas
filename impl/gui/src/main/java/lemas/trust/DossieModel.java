package lemas.trust;

import java.util.ArrayList;
import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Dossie Model")
public class DossieModel extends AbstractModel {

	protected List<Rating> dossie = new ArrayList<Rating>();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		if (isIamServer(rating)) {
			dossie.add(rating);
		}else{
			super.addRating(rating);
		}
	}

	public List<Rating> getDossie() {
		return dossie;
	}
	
	@Override
	public void setTest(Rating rating) {
		super.setTest(rating);
		myAgent.requestDossie(rating.getServer());
	}
}
