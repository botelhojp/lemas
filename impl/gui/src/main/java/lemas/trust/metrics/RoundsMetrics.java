package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import lemas.agent.LemasAgent;
import lemas.trust.data.RatingCache;
import lesma.annotations.Metrics;
import openjade.ontology.Rating;
import openjade.ontology.RatingAttribute;
import weka.core.Instance;

@Metrics(name = "Rounds Value")
public class RoundsMetrics extends AbstractIMetric {

	private double rounds = 10;
	
	private List<Double> costs = new ArrayList<Double>();
	private List<Double> benefits = new ArrayList<Double>();
	

	public RoundsMetrics() {
		LemasAgent.resetCountMessage();
	}

	public void preProcess(Instance instance) {
	}
	
	@Override
	public double prosProcess(ACLMessage msg) {
		if (msg.getContent().contains(";")) {
			System.out.println(msg.getContent());
			String[] tokens = msg.getContent().split(";");
			String options = tokens[0];
			Integer id = Integer.parseInt(tokens[1]);
			Rating r = RatingCache.remove(id);
			Clazz clazz = Classes.getClass(r.getValue());
			if (options.equals("AGREE")){
				RatingAttribute ra = (RatingAttribute) r.getAttributes().get(5);
				double value = Double.parseDouble(ra.getValue());
				add(costs, value);
				add(benefits, value * clazz.getValue());
			}			
		}
		return 100 * (value(benefits)/value(costs));
	}

	private double value(List<Double> costs2) {
		double sum = 0.0;
		for (Double double1 : costs2) {
			sum+=double1;
		}
		return sum;
	}

	private void add(List<Double> list, double value) {
		if (list.size() >= rounds){
			list.remove(0);
		}
		list.add(value);
	}

}
