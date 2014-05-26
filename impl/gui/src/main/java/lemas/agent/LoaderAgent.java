package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.HashSet;
import java.util.Set;

import lemas.agent.behaviour.LoadeBehaviour;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveSimpleMessage;

public class LoaderAgent extends OpenAgent {

	private static final long serialVersionUID = 1L;
	private Set<AID> wait = new HashSet<AID>();

	@Override
	protected void setup() {
		super.setup();
		moveContainer(OpenAgent.MAIN_CONTAINER);
		for (int index = 0; index < getArguments().length; index++) {
			System.out.println((String) getArguments()[index]);
		}
		addBehaviour(new LoadeBehaviour(this));
	}

	@ReceiveSimpleMessage(conversationId = ConversationId.LOADER)
	public void getMessage(ACLMessage msg) {
		if (wait.contains(msg.getSender())) {
			wait.remove(msg.getSender());
			System.out.println("remover " + msg.getSender());
		} else {
			throw new OpenJadeException("Agente não autorizado [" + msg.getSender().getLocalName() + "]");
		}

	}
	
	public void waiting(AID aid) {
		wait.add(aid);
	}

	public boolean nowait() {
		return wait.isEmpty();
	}

}
