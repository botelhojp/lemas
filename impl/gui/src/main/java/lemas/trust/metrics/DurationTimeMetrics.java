package lemas.trust.metrics;

import java.util.GregorianCalendar;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Round Duration (s)", file = "round_duration")
public class DurationTimeMetrics implements IMetrics {
	
	private long before;
	
	public DurationTimeMetrics(){
		before = GregorianCalendar.getInstance().getTimeInMillis();
	}

	public void preProcess(Instance instance) {
	}

	public double prosProcess(ACLMessage msg) {
		long after = GregorianCalendar.getInstance().getTimeInMillis();
		long duration = (after - before);
		before = after;
		return duration;
	}
}
