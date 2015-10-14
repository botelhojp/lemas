package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lemas.Lemas;
import openjade.ontology.RatingAttribute;
import weka.core.Instance;

public abstract class AbstractIMetric implements IMetrics {

	protected static int count = -1;

	public AbstractIMetric() {
		Lemas.seIMetrics(this);
	}

	public abstract void preProcess(Instance instance);

	public abstract double prosProcess(ACLMessage msg);

	
	protected RatingAttribute getAttributes(String key, jade.util.leap.List list) {
		for (int i = 0; i < list.size(); i++) {
			RatingAttribute ra = (RatingAttribute)list.get(i);
			if (ra.getName().equals(key))
				return ra;
		}
		return null;
	}
}
