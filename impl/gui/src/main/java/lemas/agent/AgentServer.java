package lemas.agent;

import jade.content.ContentElement;
import jade.lang.acl.ACLMessage;
import lemas.model.LemasLog;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;

public class AgentServer extends AbstractAgent {

	private static final long serialVersionUID = 1L;

	@ReceiveMatchMessage(conversationId = ConversationId.SEND_FEEDBACK, action = SendRating.class)
	public void receiveTaskRequest(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		if (rating.getServer().equals(getAID())){
			LemasLog.debug(" -  I (" + rating.getServer().getLocalName() + ") received (" + rating.getClient().getLocalName() + ") in (" + rating.getIteration() + ") the feedback <- " + rating.getValue() + " comments: " + rating.getTerm());	
		}else{
			throw new OpenJadeException("Esta avaliacao nao he minha: " + message.toString());
		}
	}
}
