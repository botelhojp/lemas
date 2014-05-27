package lemas.agent;

import jade.lang.acl.ACLMessage;
import openjade.core.annotation.ReceiveSimpleMessage;

public class AgentServer extends AbstractAgent {

	private static final long serialVersionUID = 1L;

	@ReceiveSimpleMessage(conversationId =  ConversationId.SEND_FEEDBACK)
	public void getAgreeMessage(ACLMessage msg) {
		System.out.println(" - " + msg.getSender().getLocalName() + ":" + getLocalName() + " <- " + msg.getContent());
	}
}
