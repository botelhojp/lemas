package lesma.model;

import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import openjade.ontology.Rating;

public class TrustModelData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Hashtable<AID, List<Rating>> ratings = new Hashtable<AID, List<Rating>>();
	protected Classifier classifier;
	protected List<AID> witnesses = new ArrayList<AID>();

	public TrustModelData() {
		classifier = new HoeffdingTree();
		classifier.prepareForUse();
	}

	public Hashtable<AID, List<Rating>> getRatings() {
		return ratings;
	}

	public List<AID> getWitnesses() {
		return witnesses;
	}

	public void setWitnesses(List<AID> witnesses) {
		this.witnesses = witnesses;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void addRating(AID server, Rating rating) {
		if (ratings.containsKey(server)){
			ratings.get(server).add(rating);
		}else{
			List<Rating> list = new ArrayList<Rating>();
			list.add(rating);
			ratings.put(server, list);
		}		
	}
}
