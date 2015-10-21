package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.agent.LemasAgent;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Messages Value", file = "messages_value")
public class MessagesMetrics extends AbstractIMetric {

	public MessagesMetrics() {
		LemasAgent.resetCountMessage();
	}

	public void preProcess(Instance instance) {
	}

	@Override
	public double prosProcess(ACLMessage msg) {
		return LemasAgent.countMessage();
	}

}
