package lemas.trust;

import java.util.ArrayList;
import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "ICE")
public class DossieModel extends AbstractModel {

	protected List<Rating> dossie = new ArrayList<Rating>();

	private static final long serialVersionUID = 1L;
	
	public DossieModel() {
		properties.clear();
		properties.put("DOSSIE_SIZE", "50");
	}

	@Override
	public void addRating(Rating rating) {
		if (isIamServer(rating)) {
			Integer size = Integer.parseInt(properties.getProperty("DOSSIE_SIZE"));
			dossie.add(rating);
			if (dossie.size() > size){
				dossie.remove(0);
			}
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
