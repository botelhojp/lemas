package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lemas.agent.AgentLoader;
import lemas.agent.ConversationId;
import lemas.model.LemasLog;
import lemas.model.Runner;
import lemas.util.Data;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;
import openjade.util.OpenJadeUtil;

public class LoaderBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	private static final long CACHE_SIZE = 1500;

	private AgentLoader agent;
	private List<AID> agentCache = new ArrayList<AID>();
	private BufferedReader lerArq;
	private boolean done = false;
	private String iteration = null;
	private int patterns = 0;
	private double count = 0.0;
	private double training_phase = 0.003;
	private Class<ITrustModel> trustModelClass;

	public LoaderBehaviour(AgentLoader _agent, Class<ITrustModel> trustModelClass) {
		agent = _agent;
		this.trustModelClass = trustModelClass;
		loadArff();
		readheader();
	}

	private void readheader() {
		try {
			lerArq.readLine();
			patterns = Integer.parseInt(lerArq.readLine().split(":")[1].trim());
			lerArq.readLine();
			LemasLog.info("patterns=" + patterns);
		} catch (Exception e) {
			LemasLog.erro(e);
		}
	}

	@Override
	public boolean done() {
		return done;
	}

	@Override
	public void action() {
		try {
			if (agentCache.size() > CACHE_SIZE) {
				AID deleteAid = agentCache.get(0);
				agent.sendMessage(deleteAid, ACLMessage.REQUEST, ConversationId.DO_DELETE, "");
				return;
			}
			if (agent.nowait()) {
				if (ifTraining()) {
					sendFeedback(iteration);
				} else {
					sendTest(iteration);
				}
				String line = lerArq.readLine();
				if (line != null) {
					iteration = line;
					LemasLog.info(line);
					String[] token = line.split(";");
					createAgent(new AID(token[1], false), "lemas.agent.LemasAgent");
					createAgent(new AID(token[2], false), "lemas.agent.LemasAgent");
					count++;
				} else {
					lerArq.close();
					done = true;
				}
			}
		} catch (IOException e) {
			LemasLog.erro(e);
		}
	}

	private boolean ifTraining() {
		return (count / patterns <= training_phase);
	}

	public void loadArff() {
		try {
			File file = new File(Runner.currentProject.getLoading());
			FileReader arq = new FileReader(file);
			lerArq = new BufferedReader(arq);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void sendFeedback(String line) {
		if (line != null) {
			Rating rating = makeRating(line);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(rating.getClient(), ACLMessage.REQUEST, ConversationId.TRAIN_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private void sendTest(String line) {
		if (line != null) {
			Rating rating = makeRating(line);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(rating.getClient(), ACLMessage.REQUEST, ConversationId.TEST_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private Rating makeRating(String line) {
		String[] tokens = line.split(";");
		AID clientAID = new AID(tokens[1], false);
		AID serverAID = new AID(tokens[2], false);
		int iteration = Data.strToIteration(tokens[3]);
		String term = tokens[4];
		int value = Data.strToValue(tokens[7]);
		WitnessUtil.addWitness(clientAID);
		return OpenJadeUtil.makeRating(clientAID, serverAID, iteration, term, value);
	}

	private void createAgent(AID aid, String clazz) {
		try {
			if (!agentCache.contains(aid)) {
				agent.waiting(aid);
				Object[] param = { trustModelClass };
				AgentController a = agent.getContainerController().createNewAgent(aid.getLocalName(), clazz, param);
				a.start();
				agentCache.add(aid);
			}
		} catch (StaleProxyException e) {
			LemasLog.erro(e);
		} catch (Exception e) {
			LemasLog.erro(e);
		}
	}

	public void removerCache(AID sender) {
		agentCache.remove(sender);
	}

}
