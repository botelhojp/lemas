package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lemas.Lemas;
import lemas.agent.AgentLoader;
import lemas.agent.ConversationId;
import lemas.agent.LemasAgent;
import lemas.model.LemasLog;
import lemas.model.Runner;
import lemas.util.Data;
import openjade.core.DataProvider;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;
import openjade.util.OpenJadeUtil;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class LoaderBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private static final long CACHE_SIZE = 500;

	private AgentLoader agent;
	private List<AID> agentCache = new ArrayList<AID>();
	private boolean done = false;
	private Instance instance = null;
	private int round = 0;
	private Class<ITrustModel> trustModelClass;

	private ArffReader arff;
	private BufferedReader reader;

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
			try {
				Instances data = arff.getStructure();
				data.setClassIndex(data.numAttributes() - 1);
				instance = arff.readInstance(data);
				if (instance != null) {
					System.out.println(instance);
					DataProvider.getInstance().put("DATASET", instance.dataset());
					AID client =  new AID(instance.toString(0), false);
					AID server =  new AID(instance.toString(1), false);
					agent.waiting();
					createAgent(server, "lemas.agent.LemasAgent");
					createAgent(client, "lemas.agent.LemasAgent");
					WitnessUtil.addWitness(server, client);;
					sendTest(client, instance);
				} else {
					done = true;
					stop();
				}
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void loadArff() {
		try {
			reader = new BufferedReader(new FileReader(Runner.currentProject.getLoading()));
			arff = new ArffReader(reader, 10);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void sendTest(AID client, Instance line) {
		if (line != null) {
			Rating rating = makeRating(line);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(client, ACLMessage.REQUEST, ConversationId.TEST_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private Rating makeRating(Instance instance) {
		AID clientAID = new AID(instance.toString(0), false);
		AID serverAID = new AID(instance.toString(1), false);
		String value = instance.toString(instance.numAttributes() - 1);
		return OpenJadeUtil.makeRating(clientAID, serverAID, round++, Data.instanceToRatingAttribute(instance), value);
	}

	private void createAgent(AID aid, String clazz) {
		try {
			if (!agentCache.contains(aid)) {
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

	public void stop() {
		agent.sendMessage(LemasAgent.SERVICE, ACLMessage.REQUEST, ConversationId.DO_DELETE, "");
		if (myAgent != null) {
			myAgent.removeBehaviour(this);
		}
		Lemas.sleep(2000);
		Lemas.cleanFiles();
	}

}
