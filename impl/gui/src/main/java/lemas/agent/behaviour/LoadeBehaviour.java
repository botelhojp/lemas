package lemas.agent.behaviour;

import jade.content.AgentAction;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import lemas.agent.AgentLoader;
import lemas.agent.ConversationId;
import lemas.model.Runner;
import openjade.core.OpenAgent;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.RatingAction;

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
					System.out.printf("%s\n", line);
					String[] token = line.split(";");
					loadAgent(token[1], "lemas.agent.AgentClient");
					loadAgent(token[2], "lemas.agent.AgentServer");
					line = lerArq.readLine();
				} else {
					lerArq.close();
					done = true;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
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

	private void sendFeedback(String iteration) {
		if (iteration != null) {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID(iteration.split(";")[1], false));
			msg.setConversationId(ConversationId.TRAIN_ITERATE);
			msg.setContent(iteration);
			agent.send(msg);
//			AgentAction ra = new RatingAction();
			
//			agent.sendMessage(new AID(iteration.split(";")[1], false), ACLMessage.REQUEST, ra, OpenJadeOntology.class);
//			agent.sendMessage(new AID("d", false), 0, ra, OpenJadeOntology.class) 
				
		}
	}

	private void loadAgent(String agentName, String clazz) {
		try {
			if (!agents.contains(agentName)) {
				agent.waiting(new AID(agentName, false));
				jade.core.Runtime runtime = jade.core.Runtime.instance();
				runtime.setCloseVM(true);
				ProfileImpl platform2 = new ProfileImpl("127.0.0.1", 1099, OpenAgent.MAIN_CONTAINER);
				jade.wrapper.AgentContainer ac = runtime.createAgentContainer(platform2);
				String[] param = {};
				AgentController a = ac.createNewAgent(agentName, clazz, param);
				a.start();
				agents.add(agentName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
