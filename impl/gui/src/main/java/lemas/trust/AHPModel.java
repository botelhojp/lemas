package lemas.trust;

import jade.core.AID;
import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import lemas.trust.metrics.ahp.AHPConfig;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "AHP")
public class AHPModel extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	public static AHPConfig pref = null;
	
	public AHPModel() {
		super();
		properties.put("PREFERENCIAS", "");
	}
	
	
	@Override
	public void setTest(Rating rating) {
		if (pref == null){
			pref = new AHPConfig(properties.get("PREFERENCIAS").toString());
		}
		super.setTest(rating);
		myAgent.test(rating.getServer());
	}

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}
	
	@Override
	public String test(AID aid) {
		double sum = 0.0;
		double count = 0.0;
		for(Rating r : getRatings(aid)){
			double delta = (r.getRound())/ (double) (test.getRound());
			count+=delta;
			Clazz esperado = Classes.getClass(r.getValue());			
			sum += (esperado.getValue()*delta);			
		}
		addRating(test);
		if (count == 0 ){
			count = 1;
		}
		return "NONE;" + test.getRound() + ";" + (sum / count);		
	}
}
