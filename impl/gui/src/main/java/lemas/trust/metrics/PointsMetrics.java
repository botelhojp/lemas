package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.agent.LemasAgent;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Points Value")
public class PointsMetrics implements IMetrics {

	private double sum = 0.0;

	public PointsMetrics() {
		LemasAgent.resetCountMessage();
	}

	public void preProcess(Instance instance) {
	}

	@Override
	public double prosProcess(ACLMessage msg) {
		if (msg.getContent().contains(";")) {
			String[] tokens = msg.getContent().split(";");
			String esperado = tokens[0];
			String avaliado = tokens[1];
			Clazz c1 = Classes.getClass(esperado);
			Clazz c2 = Classes.getClass(avaliado);
			double diff = c1.getValue() - c2.getValue();
			if (diff < 0){
				diff = diff * (-1);
			}
			sum += diff;
		}
		return sum;
	}

}
