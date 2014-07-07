package lesma.model;

import jade.core.AID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import moa.classifiers.trees.HoeffdingTree;
import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.Reliable;
import openjade.trust.model.Pair;

public class AbstractModel implements ITrustModel {

	protected TrustModelData data;

	protected int currentIteration;
	protected OpenAgent myAgent;
	protected Properties properties;
	protected File tmpFile;

	private static final long serialVersionUID = 1L;

	public AbstractModel() {
		data = new TrustModelData();
		properties = new Properties();
		properties.put("UNCERTAIN_RANGE", "0.2");
	}

	public void addRating(Rating rating) {
		if (data.getRatings().contains(rating.getServer())) {
			data.getRatings().get(rating.getServer()).add(rating);
		} else {
			data.getLearners().put(rating.getServer(), new HoeffdingTree());

			List<Rating> rt = new ArrayList<Rating>();
			rt.add(rating);
			data.getRatings().put(rating.getServer(), rt);

		}
	}

	public Reliable isReliable(AID agent) {
		double range = getDouble("UNCERTAIN_RANGE");
		List<Rating> list = data.getRatings().get(agent);
		if (list == null) {
			return Reliable.UNCERTAIN;
		} else {
			float sum = 0;
			for (Rating r : list) {
				sum += r.getValue();
			}
			if (sum >= range) {
				return Reliable.YES;
			}
			if (sum <= -range) {
				return Reliable.NO;
			}
			return Reliable.UNCERTAIN;
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
		tmpFile = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + myAgent.getLocalName());
	}

	public List<Rating> getRatings(AID aid) {
		return null;
	}

	public Rating addRating(AID client, AID server, int iteration, String term, float value) {
		return null;
	}

	public Enumeration<AID> getAllServer() {
		return data.getRatings().keys();
	}

	public boolean know(AID aid) {
		return data.getRatings().containsKey(aid);
	}

	public void addWitness(AID witness) {
		if (!data.getWitnesses().contains(witness) && !myAgent.equals(witness)) {
			data.getWitnesses().add(witness);
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
		return this.data.getWitnesses();
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

	public void loadSerialize() {
		try {
			if (tmpFile.exists()) {
				FileInputStream arquivoLeitura = new FileInputStream(tmpFile);
				ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
				this.data = (TrustModelData) objLeitura.readObject();
				objLeitura.close();
				arquivoLeitura.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro serialize trustmodel", e);
		}
	}

}
