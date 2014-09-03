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
import openjade.core.DataProvider;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;
import openjade.util.OpenJadeUtil;
import weka.core.Instance;

public class LoaderBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private static final long CACHE_SIZE = 500;

	private AgentLoader agent;
	private List<AID> agentCache = new ArrayList<AID>();
	private boolean done = false;
	private Instance instance = null;
	private int round = 0;
	private String currentDate = "";
	private Class<ITrustModel> trustModelClass;
	private ArffFileStream stream;

	public LoaderBehaviour(AgentLoader _agent, Class<ITrustModel> trustModelClass) {
		agent = _agent;
		this.trustModelClass = trustModelClass;
		loadArff();
	}

	@Override
	public void action() {
		if (agentCache.size() > CACHE_SIZE) {
			AID deleteAid = agentCache.get(0);
			agent.sendMessage(deleteAid, ACLMessage.REQUEST, ConversationId.DO_DELETE, "");
			return;
		}
		if (agent.nowait()) {
			sendTest(instance);
			if (stream.hasMoreInstances()) {
				instance = stream.nextInstance();
				instance = (Instance) instance.copy();
				DataProvider.getInstance().put("DATASET", instance.dataset());
				createAgent(new AID(instance.toString(0), false), "lemas.agent.LemasAgent");
				createAgent(new AID(instance.toString(1), false), "lemas.agent.LemasAgent");
			} else {
				done = true;
			}
		}
	}

	public void loadArff() {
		try {
			stream = new ArffFileStream(Runner.currentProject.getLoading(), 7);
			stream.prepareForUse();
		} catch (Exception e1) {
			e1.printStackTrace();
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

	private Rating makeRating(Instance instance) {
		AID clientAID = new AID(instance.toString(0), false);
		AID serverAID = new AID(instance.toString(1), false);
		setRound(instance.toString(2));
		String value = instance.toString(instance.numAttributes() - 1);
		WitnessUtil.addWitness(clientAID);
		return OpenJadeUtil.makeRating(clientAID, serverAID, round, Data.instanceToRatingAttribute(instance), value);
	}

	private void setRound(String date) {
		if (!currentDate.equals(date)){
			round++;
		}
		currentDate = date;		
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

	@Override
	public boolean done() {
		return done;
	}

}
