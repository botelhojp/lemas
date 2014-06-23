package lemas.agent;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.List;

import lemas.agent.behaviour.SendMessageBehaviour;
import lemas.model.LemasLog;
import lemas.model.Runner;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.RequestRating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.util.OpenJadeUtil;

public class LemasAgent extends OpenAgent {
	
	private static final long serialVersionUID = 1L;
	
		
	@SuppressWarnings("unchecked")
	protected void setup() {
//		setCodec(new SLCodec());
		super.setup();
		loadTrustModel((Class<ITrustModel>) getArguments()[0]);
		trustModel.setProperties(Runner.currentProject.getProperties());
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
		trustModel.addRating(rating);
	}	
	
	@ReceiveMatchMessage(conversationId = ConversationId.TEST_ITERATE, action = SendRating.class)
	public void receiveTestIterate(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		
		ACLMessage messageResult = new ACLMessage(ACLMessage.REQUEST);
		messageResult.setSender(getAID());
		messageResult.setConversationId(ConversationId.TEST);
		messageResult.addReceiver(new AID("lemas_loader", false));
		
		switch (trustModel.isReliable(rating.getServer())) {
		case YES:
			messageResult.setContent("1.0:" + rating.getValue());
			break;
		case NO:
			messageResult.setContent("-1.0:" + rating.getValue());
			break;
		case UNCERTAIN:
			messageResult.setContent("UNCERTAIN:" + rating.getValue());
			break;
		default:
			throw new OpenJadeException("Invalid type");
		}
		sendMessage(messageResult);
		trustModel.addRating(rating);
	}


	/**
	 * 
	 * @param message
	 * @param ce
	 */
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
	
	
	/**
	 * Atende o pedido para enviar suas avaliacoes para um determinado agente
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(performative=ACLMessage.REQUEST , action=RequestRating.class)
	public void responseWitnessRequest(ACLMessage message, ContentElement ce) {
		RequestRating rr = (RequestRating) ce;
		List list = trustModel.getRatings(rr.getAid());
		SendRating sr = new SendRating();
		sr.setRating(OpenJadeUtil.convertList(list));
		if (sr.getRating() != null){
			sendMessage(message.getSender(), ACLMessage.INFORM, sr);
		}
	}

	/**
	 * Recebe avaliacoes vindo de testemunhas
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(performative=ACLMessage.INFORM , action=SendRating.class)
	public void getRatings(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		jade.util.leap.List ratings = sr.getRating();
		for (int i = 0; i < ratings.size(); i++) {
			trustModel.addRating((Rating) ratings.get(i));
		}
	}
	
}
