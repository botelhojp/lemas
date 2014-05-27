package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import openjade.core.annotation.ReceiveSimpleMessage;

public class AgentClient extends AbstractAgent {

	private static final long serialVersionUID = 1L;

	/**
	 * Mensagem enviado pelo loader para que o agente iteraga
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.TRAIN_ITERATE)
	public void getMessage(ACLMessage msg) {
		String[] tokens = msg.getContent().split(";");
		ACLMessage fd = new ACLMessage(ACLMessage.REQUEST);
		fd.setConversationId(ConversationId.SEND_FEEDBACK);
		fd.setContent(tokens[4]+";"+tokens[7]);
		fd.addReceiver(new AID(tokens[2], false));
		send(fd);
	}
}
