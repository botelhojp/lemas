package lesma.model;

import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import moa.classifiers.Classifier;
import openjade.ontology.Rating;

public class TrustModelData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Hashtable<AID, List<Rating>> ratings = new Hashtable<AID, List<Rating>>();
	protected HashMap<AID, Classifier> classifier;
	protected List<AID> witnesses = new ArrayList<AID>();

	public TrustModelData() {
		classifier = new HashMap<AID, Classifier>();
	}

	public Hashtable<AID, List<Rating>> getRatings() {
		return ratings;
	}

	public void setRatings(Hashtable<AID, List<Rating>> ratings) {
		this.ratings = ratings;
	}

	public List<AID> getWitnesses() {
		return witnesses;
	}

	public void setWitnesses(List<AID> witnesses) {
		this.witnesses = witnesses;
	}

	public HashMap<AID, Classifier> getClassifier() {
		return classifier;
	}

	public void setClassifier(HashMap<AID, Classifier> classifier) {
		this.classifier = classifier;
	}

}
