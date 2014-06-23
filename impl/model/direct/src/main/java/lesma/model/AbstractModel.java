package lesma.model;

import jade.core.AID;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.Reliable;
import openjade.trust.model.Pair;

public class AbstractModel implements ITrustModel {
	
	protected int currentIteration;
	protected OpenAgent myAgent;
	protected Hashtable<AID, List<Rating>> ratings = new Hashtable<AID, List<Rating>>();
	protected List<AID> witnesses = new ArrayList<AID>();
	protected Properties properties;
	
	private static final long serialVersionUID = 1L;
	
	public AbstractModel(){
		properties = new Properties();
	}

	public void addRating(Rating rating) {
		if (ratings.contains(rating.getServer())){
			ratings.get(rating.getServer()).add(rating);
		}else{
			List<Rating> rt = new ArrayList<Rating>();
			rt.add(rating);
			ratings.put(rating.getServer(), rt);
		}
	}
	
	public Properties getProperties() {
		return properties;
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
	
	public Enumeration<AID> getAllServer() {
		return ratings.keys();
	}

	public boolean know(AID aid) {
		return ratings.containsKey(aid);
	}

	public void addWitness(AID witness) {
		if (!witnesses.contains(witness) && !myAgent.equals(witness)){
			witnesses.add(witness);
		}
	}
	
	/** Properties */

	protected double getDouble(String key) {
		return Double.parseDouble(properties.getProperty(key));
	}
	
	protected long getLong(String key) {
		return Long.parseLong(properties.getProperty(key));
	}
	
	protected long getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	protected String getValue(String key) {
		return properties.getProperty(key).toString();
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public List<AID> getWitnesses() {
		return this.witnesses;
	}

}
