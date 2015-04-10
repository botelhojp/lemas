package lemas.trust;

import jade.core.AID;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Central")
public class CentralModel extends AbstractModel {
	
	protected static HashMap<AID, TrustModelData> data = new HashMap<AID, TrustModelData>();
	
	private static final long serialVersionUID = 1L;

	public CentralModel() {		
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

	
	@Override
	public void setTest(Rating rating) {
		this.test = rating;		
		if (!data.containsKey(rating.getServer())) {
			data.put(rating.getServer(), new TrustModelData());
		}
		myAgent.test(rating.getServer());
	}
	
	public void addRatingFromWitness(Rating rating) {
		if (data.containsKey(rating.getServer())) {
			data.get(rating.getServer()).addRating(rating);
		} else {
			data.put(rating.getServer(), new TrustModelData());
			data.get(rating.getServer()).addRating(rating);
		}		
	}
	
	public Iterator<AID> getAllServer() {
		return data.keySet().iterator();
	}

	public boolean know(AID aid) {
		return data.containsKey(aid);
	}
	
	public void clean() {
		data.clear();
	}
	
	public List<Rating> getRatings(AID aid) {		
		return data.get(aid).getRatings();
	}

}
