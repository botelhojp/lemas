package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lemas.agent.MockClient;
import lemas.agent.MockServer;
import lemas.model.Runner;
import openjade.core.OpenAgent;

public class LoadeBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	
	private OpenAgent agent;
	private Set<String> agents = new HashSet<String>();	
	private List<String> iteration = new ArrayList<String>();

	public LoadeBehaviour(OpenAgent _agent) {
		agent = _agent;
	}

	public void action() {
		File file = new File(Runner.currentProject.getLoading());
		try {
			FileReader arq = new FileReader(file);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			while (linha != null) {
				iteration.add(linha);
				System.out.printf("%s\n", linha);
				String[] token = linha.split(";");
				load(token[1], MockClient.class.toString());
				load(token[2], MockServer.class.toString());
				linha = lerArq.readLine();
			}
			lerArq.close();
			
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(String it : iteration){
				actions(it);
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void actions(String it) {
		String[] token = it.split(";");
		
		String content = token[2] + ";" + token[4] + ";" + token[7];
		
//		agent.sendMessage(new AID(token[1], ACLMessage.REQUEST, "LOADER_ITERATE", content);
		
		
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setConversationId("LOADER_ITERATE");
		msg.setContent(token[2] + ";" + token[4] + ";" + token[7]);
		msg.addReceiver(new AID(token[1], false));		
		agent.send(msg);
	}
	
	
	private void load(String agentName, String clazz) {
		try {
			if (!agents.contains(agentName)) {
				jade.core.Runtime runtime = jade.core.Runtime.instance();
				runtime.setCloseVM(true);
				ProfileImpl platform2 = new ProfileImpl("127.0.0.1", 1099, OpenAgent.MAIN_CONTAINER);
				jade.wrapper.AgentContainer ac = runtime.createAgentContainer(platform2);
				String[] param = { "param01", "param02", "param03" };
				AgentController a = ac.createNewAgent(agentName, clazz, param);
				a.start();
				agents.add(agentName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
