package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.agent.LemasAgent;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Messages Value")
public class MessagesMetrics implements IMetrics {
	
	private long instanceCount = 0;
	
	public MessagesMetrics(){
		LemasAgent.resetCountMessage();
	}

	public void preProcess(Instance instance) {
		instanceCount++;
	}

	@Override
	public double prosProcess(ACLMessage msg) {
		return LemasAgent.countMessage()/instanceCount;
	}

}
