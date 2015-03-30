package lemas.trust;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lemas.trust.data.Data;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import openjade.ontology.Rating;
import weka.core.Instance;

public class TrustModelData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	protected Classifier classifier;
	
	
	double sum = 0.0;
	double count = 0.0;

	public TrustModelData() {
		classifier = new HoeffdingTree();
		classifier.prepareForUse();
	}

	public Classifier getClassifier() {
		return classifier;
	}
	
	public boolean getTest() {
		boolean expect = false;
		while (!ratings.isEmpty()) {
			Rating r = ratings.remove(0);			
			count++;
			if ( r.getValue().equals("pos") ){
				sum+=1.0;
				expect = true;
			}else if ( r.getValue().equals("neg") ){
				sum+=-1.0;
				expect = false;
			} else if ( r.getValue().equals("neu") ){
				sum+=0.5;
				expect = true;
			}
		}
		boolean aval = ((sum / count) >= 0.5);
		return (expect == aval);
	}	

//	public boolean getTest() {
//		boolean test = false;
//		while (!ratings.isEmpty()) {
//			Instance instance = Data.createByRating(ratings.remove(0).getAttributes());
//			test = classifier.correctlyClassifies(instance);
//			classifier.trainOnInstance(instance);
//		}
//		return test;
//	}

	/**
	 * Adiciona o avalicao
	 * 
	 * @param rating
	 */
	public void addRating(Rating rating) {
		ratings.add(rating);
	}
}
