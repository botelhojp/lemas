package lemas.trust;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;
import openjade.ontology.RatingAttribute;

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
	
	@Override
	public String test(AID aid) {
		double sum = 0.0;
		double count = 0.0;
		Clazz avaliado = null;
		for(Rating r : getRatings(aid)){
			double delta = (test.getRound() - r.getRound())/test.getRound();
			count+=delta;
			Clazz esperado = Classes.getClass(r.getValue());			
			sum += (esperado.getValue()*delta);			
		}
		if (count == 0){
			avaliado = Classes.getClasses().get(0);
		}else{
			avaliado = Classes.getClass((sum / count));
		}
		addRating(test);
		RatingAttribute ob = (RatingAttribute) test.getAttributes().get(5);
		return test.getValue() + ";" + avaliado.getName() + ";" + ob.getValue() ;
	}
}
