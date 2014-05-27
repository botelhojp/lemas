package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lemas.agent.behaviour.SendMessageBehaviour;
import lemas.model.LemasLog;
import openjade.core.OpenAgent;

public class AbstractAgent extends OpenAgent {
	
	private static final long serialVersionUID = 1L;

	protected void setup() {
		super.setup();
		LemasLog.info("created: " + getAID().getLocalName());
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setSender(getAID());
		message.setConversationId(ConversationId.LOADER);
		message.addReceiver(new AID("lemas_loader", false));
		addBehaviour(new SendMessageBehaviour(this, message));					
	}
	

}
