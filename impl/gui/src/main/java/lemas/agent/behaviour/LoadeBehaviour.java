package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
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

import lemas.agent.LoaderAgent;
import lemas.model.Runner;
import openjade.core.OpenAgent;

public class LoadeBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private LoaderAgent agent;
	private Set<String> agents = new HashSet<String>();
	private List<String> iteration = new ArrayList<String>();
	private BufferedReader lerArq;
	private boolean done = false;

	public LoadeBehaviour(LoaderAgent _agent) {
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
			System.out.println("action");
			if (agent.nowait()){
				String linha = lerArq.readLine();
				if (linha != null ) {
					iteration.add(linha);
					System.out.printf("%s\n", linha);
					String[] token = linha.split(";");
					load(token[1], "lemas.agent.MockClient");
					load(token[2], "lemas.agent.MockServer");
					linha = lerArq.readLine();
				}else{
					lerArq.close();		
					done = true;
				}				
			}
//			block();
		} catch (IOException e) {
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

//	private void actions(String it) {
//		String[] token = it.split(";");
//
//		String content = token[2] + ";" + token[4] + ";" + token[7];
//
//		// agent.sendMessage(new AID(token[1], ACLMessage.REQUEST,
//		// "LOADER_ITERATE", content);
//
//		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
//		msg.setConversationId(ConversationId.TRAIN_ITERATE);
//		msg.setContent(token[2] + ";" + token[4] + ";" + token[7]);
//		msg.addReceiver(new AID(token[1], false));
//		agent.send(msg);
//	}
	
	
//	public void waiting(AID aidAgent) {
//		int count  = 0;
//		boolean done = false;
//		agent.addWaitAgent(aidAgent);
//		while(!done || count++ <= 100){
//			try {				
//				block(1000);
//				done = !agent.containsWaitAgent(aidAgent);
//				System.out.println("waiting[" +  aidAgent.getLocalName() + "]");
//			} catch (InterruptedException e) {				
//				e.printStackTrace();
//			}
//		}
//		if (!done){
//			throw new RuntimeException("Agente nao inicializado [" + aidAgent.getLocalName() +"]");
//		}
//	}

	private void load(String agentName, String clazz) {
		try {
			if (!agents.contains(agentName)) {
				jade.core.Runtime runtime = jade.core.Runtime.instance();
				runtime.setCloseVM(true);
				ProfileImpl platform2 = new ProfileImpl("127.0.0.1", 1099, OpenAgent.MAIN_CONTAINER);
				jade.wrapper.AgentContainer ac = runtime.createAgentContainer(platform2);
				String[] param = {};
				AgentController a = ac.createNewAgent(agentName, clazz, param);
				a.start();
				agents.add(agentName);
			}
			agent.waiting(new AID(agentName, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
