package lemas.trust;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "ICE")
public class DossieModel extends AbstractModel {

	protected List<Rating> dossie = new ArrayList<Rating>();

	private static final long serialVersionUID = 1L;
	
	public DossieModel() {
		properties.clear();
		properties.put("INITIAL_BALANCE", "50000");
	}

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
	
	@Override
	public String test(AID aid) {
		double sum = 0.0;
		double count = 0.0;
		Clazz avaliado = null;
		for(Rating r : getRatings(aid)){
			double delta = (test.getRound() - r.getRound())/ (double) test.getRound();
			count+=delta;
			Clazz esperado = Classes.getClass(r.getValue());			
			sum += (esperado.getValue()*delta);			
		}
		addRating(test);
		avaliado = (count == 0)? Classes.getClasses().get(0) : Classes.getClass((sum / count));
		if (avaliado.getValue() > 0){
			return "AGREE;" + test.getRound();
		}else{
			return "REFUSE;" + test.getRound();
		}
	}
}
