package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.HashSet;
import java.util.Set;

import lemas.agent.behaviour.LoadeBehaviour;
import lemas.model.LemasLog;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveSimpleMessage;

public class AgentLoader extends OpenAgent {

	private static final long serialVersionUID = 1L;
	private Set<AID> wait = new HashSet<AID>();
	private double countTrue = 0;
	private double countFalse = 0;

	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new LoadeBehaviour(this));
	}

	@ReceiveSimpleMessage(conversationId = ConversationId.LOADER)
	public void getMessage(ACLMessage msg) {
		if (wait.contains(msg.getSender())) {
			wait.remove(msg.getSender());
		} else {
			throw new OpenJadeException("Agente não autorizado [" + msg.getSender().getLocalName() + "]");
		}

	}
	
	@ReceiveSimpleMessage(conversationId = ConversationId.TEST)
	public void getTestMessage(ACLMessage msg) {
		String[] tokens =  msg.getContent().split(":");
		String r1 =  tokens[0];
		String r2 =  tokens[1];
		if (r1.equals(r2)){
			countTrue = countTrue + 1;
		}else{
			countFalse = countFalse + 1;
		}
		updateScree();
	}

	private void updateScree() {
		double total = (countFalse + countTrue);
		LemasLog.info("total test:" + total + " ok " + countTrue + "  nok " + countFalse + " % " + (countTrue / (countFalse + countTrue)) );
	}

	public void waiting(AID aid) {
		wait.add(aid);
	}

	public boolean nowait() {
		return wait.isEmpty();
	}

}
