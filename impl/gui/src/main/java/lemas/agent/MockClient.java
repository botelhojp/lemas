package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lemas.agent.behaviour.SendMessageBehaviour;
import openjade.core.OpenAgent;
import openjade.core.annotation.ReceiveSimpleMessage;

public class MockClient extends OpenAgent {

	private static final long serialVersionUID = 1L;

	/**
	 * Inicializado do agente Mock
	 */
	@Override
	protected void setup() {
		super.setup();
		moveContainer(OpenAgent.MAIN_CONTAINER);
		System.out.println("setup " + getAID().getLocalName());
		for (int index = 0; index < getArguments().length; index++) {
			System.out.println(getAID().getLocalName() + " - " + (String) getArguments()[index]);
		}
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setSender(getAID());
		message.setConversationId(ConversationId.LOADER);
		message.addReceiver(new AID("lemas_loader", false));
		addBehaviour(new SendMessageBehaviour(this, message));					
	}
	
	/**
	 * Mensagem enviado pelo loader para que o agente iteraga
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.TRAIN_ITERATE)
	public void getMessage(ACLMessage msg) {
		String[] token = msg.getContent().split(";");
		ACLMessage fd = new ACLMessage(ACLMessage.REQUEST);
		fd.setConversationId(ConversationId.SEND_FEEDBACK);
		fd.setContent(token[1] + ":" + token[2]);
		fd.addReceiver(new AID(token[0], false));
		send(fd);
	}
}
