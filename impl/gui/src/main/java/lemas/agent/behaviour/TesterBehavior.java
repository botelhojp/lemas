package lemas.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lemas.agent.ConversationId;
import lemas.agent.LemasAgent;
import openjade.ontology.Rating;

public class TesterBehavior extends Behaviour {

	private static final long serialVersionUID = 1L;
	private LemasAgent myAgent;

	private boolean done = false;
	
	public TesterBehavior(LemasAgent agent){
		this.myAgent = agent;
	}

	@Override
	public void action() {
		if (!myAgent.getTest().isEmpty()) {
			Rating rating = myAgent.getTest().remove(0);
			ACLMessage messageResult = new ACLMessage(ACLMessage.REQUEST);
			messageResult.setSender(myAgent.getAID());
			messageResult.setConversationId(ConversationId.TEST);
			messageResult.addReceiver(new AID("lemas_loader", false));
			messageResult.setContent(myAgent.getTrustModel().test(rating).toString());
			myAgent.sendMessage(messageResult);
		}
		block(30);
	}

	@Override
	public boolean done() {
		return done;
	}
}
