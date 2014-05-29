package lemas.agent;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lemas.agent.behaviour.SendMessageBehaviour;
import lemas.model.LemasLog;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;

public class LemasAgent extends OpenAgent {

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

	@ReceiveMatchMessage(conversationId = ConversationId.TRAIN_ITERATE, action = SendRating.class)
	public void receiveTrainIterate(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		sendMessage(rating.getServer(), ACLMessage.REQUEST, ConversationId.SEND_FEEDBACK, sr, OpenJadeOntology.getInstance());
	}
	
	
	@ReceiveMatchMessage(conversationId = ConversationId.TEST_ITERATE, action = SendRating.class)
	public void receiveTestIterate(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);

		ACLMessage messageResult = new ACLMessage(ACLMessage.REQUEST);
		messageResult.setSender(getAID());
		messageResult.setConversationId(ConversationId.TEST);
		messageResult.addReceiver(new AID("lemas_loader", false));
		messageResult.setContent("1.0:" + rating.getValue());		
		sendMessage(messageResult);
		
	}


	@ReceiveMatchMessage(conversationId = ConversationId.SEND_FEEDBACK, action = SendRating.class)
	public void receiveSendFeedba(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		if (rating.getServer().equals(getAID())){
			LemasLog.debug(" -  I (" + rating.getServer().getLocalName() + ") received (" + rating.getClient().getLocalName() + ") in (" + rating.getIteration() + ") the feedback <- " + rating.getValue() + " comments: " + rating.getTerm());	
		}else{
			throw new OpenJadeException("Esta avaliacao nao he minha: " + message.toString());
		}
	}
}
