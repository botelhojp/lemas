package lemas.trust;

import jade.core.AID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import lemas.agent.LemasAgent;
import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.model.Pair;

public class AbstractModel implements ITrustModel {

	protected HashMap<AID, TrustModelData> data = new HashMap<AID, TrustModelData>();
	protected int currentIteration;
	protected LemasAgent myAgent;
	protected List<AID> witnessList;
	protected Properties properties;
	protected File tmpFile;

	private static final long serialVersionUID = 1L;

	public AbstractModel() {
		properties = new Properties();
		witnessList = new ArrayList<AID>();
	}

	public Boolean test(AID aid) {
		return data.get(aid).getTest();
	}

	public void addRating(Rating rating, boolean direct) {
		if (isIamClient(rating) || !direct) {
			if (data.containsKey(rating.getServer())) {
				data.get(rating.getServer()).addRating(rating);
			} else {
				TrustModelData tmd = new TrustModelData();
				tmd.addRating(rating);
				data.put(rating.getServer(), tmd);
			}
		}
	}

	protected boolean isIamClient(Rating rating) {
		return (rating != null && rating.getClient().equals(myAgent.getAID()));
	}

	protected boolean isIamServer(Rating rating) {
		return (rating != null && rating.getServer().equals(myAgent.getAID()));
	}

	// public void reset() {
	// data.resetClassifier();
	// }

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
		this.myAgent = (LemasAgent) agent;
		tmpFile = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + myAgent.getLocalName() + Constants.AGENT_FILE_EXTENSION);
	}

	public Rating addRating(AID client, AID server, int iteration, String term, float value) {
		return null;
	}

	public Iterator<AID> getAllServer() {
		return data.keySet().iterator();
	}

	public boolean know(AID aid) {
		return data.containsKey(aid);
	}

	public void addWitness(AID witness) {
		if (!witnessList.contains(witness) && !myAgent.equals(witness)) {
			witnessList.add(witness);
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
		return witnessList;
	}

	public void serialize() {
		try {
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
			FileOutputStream arquivoGrav = new FileOutputStream(tmpFile);
			ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
			objGravar.writeObject(data);
			objGravar.flush();
			objGravar.close();
			arquivoGrav.flush();
			arquivoGrav.close();
		} catch (IOException e) {
			throw new RuntimeException("Erro serialize trustmodel", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void loadSerialize() {
		try {
			if (tmpFile.exists()) {
				FileInputStream arquivoLeitura = new FileInputStream(tmpFile);
				ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
				this.data = (HashMap<AID, TrustModelData>) objLeitura.readObject();
				objLeitura.close();
				arquivoLeitura.close();
				tmpFile.delete();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro serialize trustmodel", e);
		}
	}

	public void findReputation(AID server) {
	}

	public List<Rating> getDossie() {
		return null;
	}
}
