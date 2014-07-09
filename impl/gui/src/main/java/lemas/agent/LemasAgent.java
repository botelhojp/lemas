package lemas.agent;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.util.List;

import lemas.model.LemasLog;
import lemas.model.Runner;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.core.behaviours.SendMessageBehaviour;
import openjade.ontology.Rating;
import openjade.ontology.RequestRating;
import openjade.ontology.SendRating;
import openjade.ontology.WitnessRequest;
import openjade.ontology.WitnessResponse;
import openjade.trust.ITrustModel;
import openjade.util.OpenJadeUtil;

@SuppressWarnings("rawtypes")
public class LemasAgent extends OpenAgent {

	private static final long serialVersionUID = 1L;

	/**
	 * ============================================================= TODOS OS
	 * MODELOS =============================================================
	 */

	@SuppressWarnings("unchecked")
	protected void setup() {
		// setCodec(new SLCodec());
		super.setup();
		loadTrustModel((Class<ITrustModel>) getArguments()[0]);
		trustModel.setProperties(Runner.currentProject.getProperties());
		trustModel.loadSerialize();
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
		sendMessage(rating.getServer(), ACLMessage.REQUEST, ConversationId.SEND_FEEDBACK, sr);
		trustModel.addRating(rating);
	}
	
	@ReceiveSimpleMessage(performative=ACLMessage.REQUEST,  conversationId = ConversationId.DO_DELETE)
	public void dead(ACLMessage message) {
		trustModel.serialize();
		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.DO_DELETE, "");
		this.doDelete();
	}	

	/**
	 * Cliente enviando sua opiniao ao agente loader
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.TEST_ITERATE, action = SendRating.class)
	public void receiveTestIterate(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);

		ACLMessage messageResult = new ACLMessage(ACLMessage.REQUEST);
		messageResult.setSender(getAID());
		messageResult.setConversationId(ConversationId.TEST);
		messageResult.addReceiver(new AID("lemas_loader", false));
		messageResult.setContent(trustModel.test(rating).toString());
		sendMessage(messageResult);
		trustModel.addRating(rating);
	}

	/**
	 * Servidor recebendo um feedback e quarda referencia da testemunha
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.SEND_FEEDBACK, action = SendRating.class)
	public void receiveFeedback(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		trustModel.addWitness(message.getSender());
		Rating rating = (Rating) sr.getRating().get(0);
		if (!rating.getServer().equals(getAID())) {			
			throw new OpenJadeException("Esta avaliacao nao he minha: " + message.toString());
		}
	}

	/**
	 * ============================================================= INDIRETO
	 * =============================================================
	 */

	/**
	 * Atende o pedido para enviar suas avaliacoes para um determinado agente
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ACLMessage.REQUEST + "", performative = ACLMessage.REQUEST, action = RequestRating.class)
	public void responseWitnessRequest(ACLMessage message, ContentElement ce) {
		RequestRating rr = (RequestRating) ce;
		List list = trustModel.getRatings(rr.getAid());
		SendRating sr = new SendRating();
		sr.setRating(OpenJadeUtil.convertList(list));
		if (sr.getRating() != null) {
			sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.SEND_RATING, sr);
		}
	}

	/**
	 * Recebe avaliacoes vindo de testemunhas
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.SEND_RATING, performative = ACLMessage.INFORM, action = SendRating.class)
	public void getRatings(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		jade.util.leap.List ratings = sr.getRating();
		for (int i = 0; i < ratings.size(); i++) {
			trustModel.addRating((Rating) ratings.get(i));
		}
	}

	/**
	 * ============================================================= REGRET
	 * =============================================================
	 */

	@Override
	public void findWitnesses(AID server) {
		System.out.println(getAID());
		System.out.println(server);
		super.findWitnesses(server);
		WitnessRequest wr = new WitnessRequest();
		wr.setAid(server);
		sendMessage(server, ACLMessage.REQUEST, ConversationId.REQUEST_WITNESS, wr);
	}

	@ReceiveMatchMessage(conversationId = ConversationId.REQUEST_WITNESS, performative = ACLMessage.REQUEST, action = WitnessRequest.class)
	public void getResponseWitnessRequest(ACLMessage message, ContentElement ce) {
		WitnessRequest wr = (WitnessRequest) ce;
		WitnessResponse wrs = new WitnessResponse();
		List<Rating> l = trustModel.getRatings(wr.getAid());
		if (l != null) {
			for (Rating r : l) {
				wrs.getWitnesses().add(r);
			}
		}
		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.REQUEST_WITNESS, wr);
	}

	@ReceiveMatchMessage(performative = ACLMessage.INFORM, action = WitnessResponse.class, conversationId = "dfdsfs")
	public void getReceiveWitnessRequest(ACLMessage message, ContentElement ce) {
		WitnessResponse wr = (WitnessResponse) ce;
		Iterator it = wr.getAllWitnesses();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			trustModel.addWitness(aid);
		}
	}

}
