package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.agent.LemasAgent;
import lemas.trust.data.RatingCache;
import lemas.util.CountList;
import lesma.annotations.Metrics;
import openjade.ontology.Rating;
import openjade.ontology.RatingAttribute;
import weka.core.Instance;

@Metrics(name = "Rounds Value", file = "rounds_value")
public class RoundsMetrics extends AbstractIMetric {

	private int rounds = 100;
	
	private CountList costs = new CountList(rounds);
	private CountList benefits = new CountList(rounds);
	

	public RoundsMetrics() {
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
			Rating r = RatingCache.remove(id);
			Clazz clazz = Classes.getClass(r.getValue());
			if (options.equals("AGREE")){				
				RatingAttribute ra = (RatingAttribute) getAttributes("cost", r.getAttributes());
				double value = Double.parseDouble(ra.getValue());
				costs.add(value);
				benefits.add(value * clazz.getValue());
			}			
		}
		return 100 * (benefits.total()/costs.total());
	}

}
