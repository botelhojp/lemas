package lemas.trust;

import jade.core.AID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import lemas.agent.LemasAgent;
import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.model.Pair;

public  class AbstractModel implements ITrustModel {

	protected HashMap<AID, TrustModelData> data;
	protected int currentIteration;
	protected LemasAgent myAgent;
	protected Properties properties;
	protected File tmpFile;
	protected Rating test;

	private static final long serialVersionUID = 1L;

	public AbstractModel() {
		data = new HashMap<AID, TrustModelData>();
		properties = new Properties();
	}

	@Override
	public String test(AID aid) {
		double sum = 0.0;
		double count = 0.0;
		Clazz avaliado = null;
		for(Rating r : getRatings(aid)){
			double delta = (test.getRound() - r.getRound())/ (double) test.getRound();
			count+=delta;
			Clazz esperado = Classes.getClass(r.getValue());			
			sum += (esperado.getValue()*delta);			
		}
		addRating(test);
		avaliado = (count == 0)? Classes.getClasses().get(0) : Classes.getClass((sum / count));
		if (avaliado.getValue() > 0){
			return "AGREE;" + test.getRound();
		}else{
			return "REFUSE;" + test.getRound();
		}
	}
	
	public void addRating(Rating rating) {
		if (isIamClient(rating)) {
			addRatingFromWitness(rating);
		}
	}
	
	public void addRatingFromWitness(Rating rating) {
		if (data.containsKey(rating.getServer())) {
			data.get(rating.getServer()).addRating(rating);
		} else {
			data.put(rating.getServer(), new TrustModelData());
			data.get(rating.getServer()).addRating(rating);
		}		
	}

	protected boolean isIamClient(Rating rating) {
		return (rating != null && rating.getClient().equals(myAgent.getAID()));
	}

	protected boolean isIamServer(Rating rating) {
		return (rating != null && rating.getServer().equals(myAgent.getAID()));
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

	public void clean() {
		data.clear();
	}

	@Override
	public void setTest(Rating rating) {
		this.test = rating;		
		if (!data.containsKey(rating.getServer())) {
			data.put(rating.getServer(), new TrustModelData());
		}
	}
	
	public List<Rating> getRatings(AID aid) {		
		return data.get(aid).getRatings();
	}
}
