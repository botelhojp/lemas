package lemas.trust;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lemas.trust.data.Data;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import openjade.ontology.Rating;
import weka.core.Instance;

public class TrustModelData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	protected Classifier classifier;

	public TrustModelData() {
		classifier = new HoeffdingTree();
		classifier.prepareForUse();
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public boolean getTest() {
		boolean test = false;
		while (!ratings.isEmpty()) {
			Instance instance = Data.createByRating(ratings.remove(0).getAttributes());
			test = classifier.correctlyClassifies(instance);
			classifier.trainOnInstance(instance);
		}
		return test;
	}

	/**
	 * Adiciona o avalicao
	 * 
	 * @param rating
	 */
	public void addRating(Rating rating) {
		ratings.add(rating);
	}
}
