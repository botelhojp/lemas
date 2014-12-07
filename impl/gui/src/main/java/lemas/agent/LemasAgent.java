package lemas.agent;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.util.List;

import lemas.agent.behaviour.SendMessageBehavior;
import lemas.model.LemasLog;
import lemas.model.Runner;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.ontology.Rating;
import openjade.ontology.RequestRating;
import openjade.ontology.SendRating;
import openjade.ontology.WitnessResponse;
import openjade.trust.ITrustModel;

public class LemasAgent extends OpenAgent {

	private static final long serialVersionUID = 1L;
	public static final String SERVICE = "LEMAS";
	private SendMessageBehavior tb;

	/**
	 * Inicialização
	 */
	@SuppressWarnings("unchecked")
	protected void setup() {
		// setCodec(new SLCodec());
		super.setup();
		loadTrustModel((Class<ITrustModel>) getArguments()[0]);
		trustModel.setProperties(Runner.currentProject.getProperties());
		trustModel.loadSerialize();
		LemasLog.info("created: " + getAID().getLocalName());
		tb = new SendMessageBehavior(this);
		addBehaviour(tb);
		registerService(SERVICE);
	}

	/**
	 * Se destroi quando solicitado pelo agente loader
	 * 
	 * @param message
	 */
	@ReceiveSimpleMessage(performative = ACLMessage.REQUEST, conversationId = ConversationId.DO_DELETE)
	public void dead(ACLMessage message) {
		super.deregister(SERVICE);
		trustModel.serialize();
		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.DO_DELETE, "");
		this.doDelete();
	}
	
	// Operacoes essencias
	
	public void testLastRating(AID aid){
		tb.test(aid);
	}	
	
	public void requestDossie(AID aid){
		tb.requestDossie(aid);
	}

	// ==== GERAL ====
	
	/**
	 * Cliente envia feedback ao servidor e ao loader
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.START_ITERATE, action = SendRating.class)
	public void receiveTestIterate(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		sendMessage(rating.getServer(), ACLMessage.REQUEST, ConversationId.SEND_FEEDBACK, sr);
		trustModel.addRating(rating, true);		
	}
	
	/**
	 * Servidor recebendo um feedback e quarda referencia da testemunha e
	 * avaliacao se do modelo dossie
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.SEND_FEEDBACK, action = SendRating.class)
	public void receiveFeedback(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Rating rating = (Rating) sr.getRating().get(0);
		trustModel.addWitness(message.getSender());
		trustModel.addRating(rating, true);
		if (!rating.getServer().equals(getAID())) {
			throw new OpenJadeException("Esta avaliacao nao he minha: " + message.toString());
		}
	}
	
	// ==== DOSSIE ====
	
	/**
	 * Recebe um pedido para enviar seu dossie
	 * 
	 * @param message
	 */
	@ReceiveSimpleMessage(performative = ACLMessage.REQUEST, conversationId = ConversationId.GET_DOSSIE)
	public void requestDossie(ACLMessage message) {
		SendRating sr = new SendRating();
		List<Rating> rs = trustModel.getDossie();
		for (Rating rating : rs) {
			sr.addRating(rating);
		}
		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.GET_DOSSIE, sr);
	}

	/**
	 * Recebe o dossie solicitado
	 * 
	 * @param message
	 */
	@ReceiveMatchMessage(performative = ACLMessage.INFORM, conversationId = ConversationId.GET_DOSSIE, action = SendRating.class)
	public void responseDossie(ACLMessage message, ContentElement ce) {
		SendRating sr = (SendRating) ce;
		Iterator it = sr.getAllRating();
		trustModel.clean();
		while (it.hasNext()) {			
			Rating rating = (Rating) it.next();
			if (!rating.getClient().equals(this.getAID())){
				trustModel.addRating(rating, false);	
			}
		}
		tb.test(message.getSender());
	}

	// ==== INDIRETO ====

	/**
	 * Recebe do loader um conjunto de testemunhas
	 * 
	 * @param message
	 * @param ce
	 */
	private static int done = 0;
	@ReceiveMatchMessage(conversationId = ConversationId.GET_INDIRECT, performative = ACLMessage.INFORM, action = WitnessResponse.class)
	public void responseWitnessRequest2(ACLMessage message, ContentElement ce) {
		WitnessResponse wr = (WitnessResponse) ce;
		Iterator it = wr.getAllWitnesses();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			RequestRating rr = new RequestRating();
			rr.setAid(wr.getServer());
			done++;
			sendMessage(aid, ACLMessage.REQUEST, ConversationId.REQUEST_REPUTATION, rr);
		}
	}

	/**
	 * Informa, como uma testemunha, a reputação de um certo agente.
	 * 
	 * @param message
	 * @param ce
	 */
	
	@ReceiveMatchMessage(conversationId = ConversationId.REQUEST_REPUTATION, performative = ACLMessage.REQUEST, action = RequestRating.class)
	public void responseWitnessRequest3(ACLMessage message, ContentElement ce) {
//		RequestRating rr = (RequestRating) ce;
//		List<Rating> list = getTrustModel().getRatings(rr.getAid());
//		SendRating sr = new SendRating();
//		for (Rating rating : list) {
//			sr.addRating(rating);
//		}
//		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.REQUEST_REPUTATION, sr);
	}
	
	/**
	 * Recebe testemunhos sobre o agente solicitado
	 * 
	 * @param message
	 * @param ce
	 */
	@ReceiveMatchMessage(conversationId = ConversationId.REQUEST_REPUTATION, performative = ACLMessage.INFORM, action = SendRating.class)
	public void responseWitnessRequest4(ACLMessage message, ContentElement ce) {
		done--;
		SendRating rr = (SendRating) ce;
		Iterator it = rr.getAllRating();
		while (it.hasNext()) {
//			Rating rt = (Rating) it.next();
//			listPendingRating.add(rt);
		}
		if (done == 0){
			tb.resume();
		}
	}
}
