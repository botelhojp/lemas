package lemas.trust;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import openjade.ontology.Rating;

public class TrustModelData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	protected Hashtable<Integer, Rating> hash = new Hashtable<Integer, Rating>();

	public TrustModelData() {
	}

	public String getTest(Rating test) {		
		double sum = 0.0;
		double count = 0.0;
		Clazz avaliado = null;
		for(Rating r : ratings){
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
		return test.getValue() + ";" + avaliado.getName() ;
	}

	/**
	 * Adiciona o avalicao
	 * 
	 * @param rating
	 */
	public void addRating(Rating rating) {
		//Garante que não haverá avaliacoes no conjunto
		if (!hash.containsKey(rating.getRound())){
			ratings.add(rating);	
			hash.put(rating.getRound(), rating);
		}		
	}

	public List<Rating> getRatings() {
		return ratings;
	}
}
