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

	double sum = 0.0;
	double count = 0.0;

	public TrustModelData() {
	}

	public String getTest(Rating test) {		
		if (!ratings.isEmpty()) {
			Clazz avaliado = null;
			if (count == 0){
				//na primeira vez ele retorna a primeira classe
				avaliado = Classes.getClasses().get(0);
			}else{
				avaliado = Classes.getClass((sum / count));	
			}						
			Clazz esperado = Classes.getClass(test.getValue());
			count++;
			sum += esperado.getValue();
			return esperado.getName() + ";" + avaliado.getName() ;
		}
		return null;
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
}
