package lemas.agent.behaviour;

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

public class LoadeBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private AgentLoader agent;
	private Set<String> agents = new HashSet<String>();
	private BufferedReader lerArq;
	private boolean done = false;

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
			String client = null;
			String server = null;
			if (agent.nowait()) {
				actions(client, server);
				String linha = lerArq.readLine();
				if (linha != null) {
					System.out.printf("%s\n", linha);
					String[] token = linha.split(";");
					client = token[1];
					server = token[2];
					loadAgent(client, "lemas.agent.AgentClient");
					loadAgent(server, "lemas.agent.AgentServer");
					linha = lerArq.readLine();
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

	private void actions(String client, String server) {
		if (client != null && server != null) {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID(client, false));
			msg.setConversationId(ConversationId.TRAIN_ITERATE);
			msg.setContent(server);
			agent.send(msg);
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
