package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import openjade.core.OpenAgent;
import openjade.core.annotation.ReceiveSimpleMessage;

public class MockServer extends OpenAgent {

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
	}

	/**
	 * Recebimento de um feedback
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = "FEEDBACK")
	public void getAgreeMessage(ACLMessage msg) {
		System.out.println(" - " + getLocalName() + " <- " + msg.getContent());
	}

	/**
	 * Mensagem enviado pelo loader para que o agente iteraga
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = "LOADER_ITERATE")
	public void getMessage(ACLMessage msg) {
		String[] token = msg.getContent().split(";");
		ACLMessage fd = new ACLMessage(ACLMessage.REQUEST);
		fd.setConversationId(Conversations.SEND_FEEDBACK);
		fd.setContent(token[1] + ":" + token[2]);
		fd.addReceiver(new AID(token[0], false));
		send(fd);
	}

}
