package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lemas.agent.ConversationId;
import lemas.agent.LemasAgent;

public class SendMessageBehavior extends Behaviour {

	private static final long serialVersionUID = 1L;
	private LemasAgent myAgent;
	private boolean resume = true;
	private boolean sendTest = false;
	private boolean requestDossie = false;
	private boolean findWitness = false;
	private AID aid;

	public SendMessageBehavior(LemasAgent agent) {
		this.myAgent = agent;
	}

	@Override
	public void action() {
		if (resume) {
			if (sendTest) {
				myAgent.sendMessage(makeMessage(new AID("lemas_loader", false), ConversationId.TEST, "" + myAgent.getTrustModel().test(aid)));
				sendTest = false;
			} 
			if (requestDossie){
				myAgent.sendMessage(makeMessage(aid, ConversationId.GET_DOSSIE, ""));
				requestDossie = false;
				aid = null;
			}
			if (findWitness){
				myAgent.sendMessage(makeMessage(new AID("lemas_loader", false), ConversationId.NEXT, ""));
				findWitness = false;
			}
		}
		block(50);
	}
	
	private ACLMessage makeMessage(AID receiver, String conversation, String content){
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(myAgent.getAID());
		msg.setConversationId(conversation);
		msg.addReceiver(receiver);
		msg.setContent(content);		
		return msg;
	}

	@Override
	public boolean done() {
		return false;
	}

	public void resume() {
		resume = true;
	}
	
	public void test(AID aid) {
		this.aid = aid;
		sendTest = true;
	}

	public void findWitness(AID aidFind) {
		findWitness = true;
	}
	
	public void requestDossie(AID aid) {
		this.aid = aid;
		requestDossie = true;
	}

	public void pause() {
		resume = false;
	}

}
