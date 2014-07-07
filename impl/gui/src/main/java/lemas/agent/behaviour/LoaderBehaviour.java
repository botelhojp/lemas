package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

import lemas.agent.AgentLoader;
import lemas.agent.ConversationId;
import lemas.model.LemasLog;
import lemas.model.Runner;
import lemas.util.Data;
import moa.streams.ArffFileStream;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;
import openjade.util.OpenJadeUtil;
import weka.core.Instance;

public class LoaderBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private static final long CACHE_SIZE = 1500;

	private AgentLoader agent;
	private List<AID> agentCache = new ArrayList<AID>();
	private boolean done = false;
	private Instance iteration = null;
	private int patterns = 0;
	private double count = 0.0;
	private double training_phase = 0.003;
	private Class<ITrustModel> trustModelClass;
	private ArffFileStream stream;

	public LoaderBehaviour(AgentLoader _agent, Class<ITrustModel> trustModelClass) {
		agent = _agent;
		this.trustModelClass = trustModelClass;
		loadArff();
	}

	@Override
	public boolean done() {
		return done;
	}

	@Override
	public void action() {
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
			Instance trainInst  = stream.nextInstance();
			if (trainInst != null) {
				iteration = trainInst;
				LemasLog.info(trainInst.toString());
				createAgent(new AID(trainInst.toString(0), false), "lemas.agent.LemasAgent");
				createAgent(new AID(trainInst.toString(1), false), "lemas.agent.LemasAgent");
				count++;
			} else {
				done = true;
			}
		}
	}

	private boolean ifTraining() {
		return (count / patterns <= training_phase);
	}


	public void loadArff() {
		try {
			stream = new ArffFileStream(Runner.currentProject.getLoading(), 7);
			stream.prepareForUse();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void sendFeedback(Instance instance) {
		if (instance != null) {
			Rating rating = makeRating(instance);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(rating.getClient(), ACLMessage.REQUEST, ConversationId.TRAIN_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private void sendTest(Instance line) {
		if (line != null) {
			Rating rating = makeRating(line);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(rating.getClient(), ACLMessage.REQUEST, ConversationId.TEST_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private Rating makeRating(Instance line) {
		AID clientAID = new AID(line.toString(0), false);
		AID serverAID = new AID(line.toString(1), false);
		int iteration = Data.strToIteration(line.toString(2));
		String term = line.toString(3);
		int value = Data.strToValue(line.toString(6));
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
