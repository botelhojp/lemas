package lemas.agent;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.Enumeration;

import lemas.agent.behaviour.SendMessageBehaviour;
import lemas.model.LemasLog;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.ontology.WitnessRequest;
import openjade.trust.ITrustModel;

public class LemasAgent extends OpenAgent {
	
	private static final long serialVersionUID = 1L;
	
		
	@SuppressWarnings("unchecked")
	protected void setup() {
//		setCodec(new SLCodec());
		super.setup();
		loadTrustModel((Class<ITrustModel>) getArguments()[0]);
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
	
	@Override
	public void searchWitnesses(AID server) {
		LemasLog.debug("searchWitnesses: " + server.getLocalName());
		WitnessRequest wr = new WitnessRequest();
		wr.setAid(server);
		Enumeration<AID> e = trustModel.getAllServer();
		while (e.hasMoreElements()) {
			AID aid = (AID) e.nextElement();
			sendMessage(aid, ACLMessage.REQUEST, ConversationId.SEARCH_WITNESSES, wr, openJadeOntology);
		}
	}	
	
	@ReceiveMatchMessage(conversationId=ConversationId.SEARCH_WITNESSES, action=WitnessRequest.class)
	public void responseWitnessRequest(ACLMessage message, ContentElement ce) {
		WitnessRequest wr = (WitnessRequest) ce;
		if(trustModel.know(wr.getAid())){
			sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.SEARCH_WITNESSES, "");
		}		
	}
	
	
	@ReceiveSimpleMessage(conversationId=ConversationId.SEARCH_WITNESSES, performative=ACLMessage.INFORM)
	public void response(ACLMessage message) {
		LemasLog.debug("(xx)"+message.getSender().toString());
		trustModel.addWitness(message.getSender());
	}
	
	
}
