package lemas.agent;

import jade.content.ContentElement;
import jade.lang.acl.ACLMessage;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;

public class AgentClient extends AbstractAgent {

	private static final long serialVersionUID = 1L;

	/**
	 * Mensagem enviado pelo loader para treinamento
	 * 
	 * @param msg
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.TRAIN_ITERATE, action = SendRating.class)
	public void receiveTaskRequest(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		sendMessage(rating.getServer(), ACLMessage.REQUEST, ConversationId.SEND_FEEDBACK, sr, OpenJadeOntology.getInstance());
	}
}
