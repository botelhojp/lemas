package lemas.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.KillContainer;
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

import lemas.model.Runner;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;

public class LoaderAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private Set<String> agents = new HashSet<String>();
	private List<String> iteration = new ArrayList();

	@Override
	protected void setup() {
		super.setup();
		moveContainer(OpenAgent.MAIN_CONTAINER);
		for (int index = 0; index < getArguments().length; index++) {
			System.out.println((String) getArguments()[index]);
		}

		addBehaviour(new Behaviour() {
			
			@Override
			public boolean done() {
				return true;
			}
			
			@Override
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
						
						load(token[1]);
						load(token[2]);
						//load(token[1], token[2], token[4], token[7]) ;
						linha = lerArq.readLine();
					}
					
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.setContent(token[2] + ";" + token[4] + ";" + token[7]);
				msg.addReceiver(new AID(token[1], false));
				send(msg);
			}
		});

	}

	private void load(String agentName) {
		try {
			if (!agents.contains(agentName)) {
				jade.core.Runtime runtime = jade.core.Runtime.instance();
				runtime.setCloseVM(true);
				ProfileImpl platform2 = new ProfileImpl("127.0.0.1", 1099, OpenAgent.MAIN_CONTAINER);
				jade.wrapper.AgentContainer ac = runtime.createAgentContainer(platform2);
				String[] param = { "param01", "param02", "param03" };
				AgentController a = ac.createNewAgent(agentName, "lemas.agent.Mock", param);
				a.start();
				agents.add(agentName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void moveContainer(String to) {
		try {
			String from = getContainerController().getContainerName();
			if (!to.equals(from)) {
				ContainerID cid = new ContainerID(to, null);
				doMove(cid);
				KillContainer kill = new KillContainer();
				kill.setContainer(new ContainerID(from, null));
				Codec codec = new SLCodec();
				Ontology jmo = JADEManagementOntology.getInstance();
				getContentManager().registerLanguage(codec);
				getContentManager().registerOntology(jmo);
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.addReceiver(getAMS());
				msg.setLanguage(codec.getName());
				msg.setOntology(jmo.getName());
				getContentManager().fillContent(msg, new Action(getAID(), kill));
				send(msg);
			}
		} catch (Exception e) {
			throw new OpenJadeException(OpenAgent.MAIN_CONTAINER, e);
		}
	}

}
