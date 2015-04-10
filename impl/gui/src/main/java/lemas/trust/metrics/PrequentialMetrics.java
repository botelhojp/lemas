package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Success Rate Prequential")
public class PrequentialMetrics extends AbstractIMetric {
	
	private double count = 50;
	private List<Boolean> prequential = new ArrayList<Boolean>();

	public void preProcess(Instance instance) {
	}

	public double prosProcess(ACLMessage msg) {
		if (prequential.size() == count){
			prequential.remove(0);
		}
		prequential.add(Boolean.parseBoolean(msg.getContent()));
		return calcValue(prequential);
	}

	private double calcValue(List<Boolean> list) {
		double countFalse = 0;
		for (Boolean test : list) {
			if (!test) {
				countFalse++;
			}	
		}
		return (1.00 - countFalse/list.size());
	}

}
