package lesma.model;

import jade.core.AID;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.Reliable;
import openjade.trust.model.Pair;

public class AbstractModel implements ITrustModel {
	
	protected int currentIteration;
	protected OpenAgent myAgent;
	protected Hashtable<AID, List<Rating>> ratings = new Hashtable<AID, List<Rating>>();
	protected Set<AID> witness = new HashSet<AID>();
	
	private static final long serialVersionUID = 1L;

	public void addRating(Rating rating) {
		if (ratings.contains(rating.getServer())){
			ratings.get(rating.getServer()).add(rating);
		}else{
			List<Rating> rt = new ArrayList<Rating>();
			rt.add(rating);
			ratings.put(rating.getServer(), rt);
		}
	}

	public void currentIteration(int iteration) {
		currentIteration = iteration;
	}

	public List<Pair> getPairs(String[] terms) {
		return null;
	}

	public float getValue(AID server) {
		return 0;
	}

	public void setAgent(OpenAgent agent) {
		this.myAgent = agent;
	}

	public List<Rating> getRatings(AID aid) {
		return null;
	}

	public Rating addRating(AID client, AID server, int iteration, String term, float value) {
		return null;
	}

	public Reliable isReliable(AID agent) {
		return Reliable.UNCERTAIN;
	}

	@Override
	public Enumeration<AID> getAllServer() {
		return ratings.keys();
	}

	@Override
	public boolean know(AID aid) {
		return ratings.containsKey(aid);
	}

	@Override
	public void addWitness(AID sender) {
		witness.add(sender);
	}

}
