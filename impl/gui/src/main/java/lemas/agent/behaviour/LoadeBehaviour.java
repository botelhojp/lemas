package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;

import lemas.agent.AgentLoader;
import lemas.agent.ConversationId;
import lemas.model.LemasLog;
import lemas.model.Runner;
import lemas.util.Data;

public class LoadeBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private AgentLoader agent;
	private Set<String> agents = new HashSet<String>();
	private BufferedReader lerArq;
	private boolean done = false;
	private String iteration = null;

	public LoadeBehaviour(AgentLoader _agent) {
		agent = _agent;
		loadArff();
	}

	@Override
	public boolean done() {
		return done;
	}

	@Override
	public void action() {
		try {
			if (agent.nowait()) {
				sendFeedback(iteration);
				String line = lerArq.readLine();
				if (line != null) {
					iteration = line;
					LemasLog.info(line);
					String[] token = line.split(";");
					createAgent(token[1], "lemas.agent.LemasAgent");
					createAgent(token[2], "lemas.agent.LemasAgent");
				} else {
					lerArq.close();
					done = true;
				}
			}
		} catch (Throwable e) {
			LemasLog.erro(e);
		}
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
			Rating rating = strToRating(line);
			SendRating sr = new SendRating();
			sr.addRating(rating);
			agent.sendMessage(rating.getClient(), ACLMessage.REQUEST, ConversationId.TRAIN_ITERATE, sr, OpenJadeOntology.getInstance());
		}
	}

	private Rating strToRating(String line) {
		String[] tokens = line.split(";");
		AID clientAID = new AID(tokens[1], false);
		AID serverAID = new AID(tokens[2], false);
		int iteration = Data.strToIteration(tokens[3]);
		String term = tokens[4];
		int value = Data.strToValue(tokens[7]);
		return new Rating(clientAID, serverAID, iteration, term, value);
	}

	private void createAgent(String agentName, String clazz) {
		try {
			if (!agents.contains(agentName)) {
				agent.waiting(new AID(agentName, false));
				String[] param = {};
				AgentController a = agent.getContainerController().createNewAgent(agentName, clazz, param);
				a.start();
				agents.add(agentName);
			}
		} catch (Exception e) {
			LemasLog.erro(e);
		}
	}

}
