package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.agent.LemasAgent;
import lemas.trust.data.RatingCache;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Refused Value", file = "refused_value")
public class AgreeMetrics extends AbstractIMetric {

	private double refused = 0.0;
	

	public AgreeMetrics() {
		LemasAgent.resetCountMessage();
	}

	public void preProcess(Instance instance) {
	}
	
	@Override
	public double prosProcess(ACLMessage msg) {
		if (msg.getContent().contains(";")) {
			String[] tokens = msg.getContent().split(";");
			String options = tokens[0];
			Integer id = Integer.parseInt(tokens[1]);
			RatingCache.remove(id);
			if (!options.equals("AGREE")){
				refused++;
			}			
		}
		return refused;
	}

}
