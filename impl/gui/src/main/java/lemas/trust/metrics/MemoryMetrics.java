package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Memory Used (Mb)", file = "memory_used")
public class MemoryMetrics implements IMetrics {

	public void preProcess(Instance instance) {
	}

	public double prosProcess(ACLMessage msg) {
		Runtime r = Runtime.getRuntime();
		return (r.totalMemory() -  Runtime.getRuntime().freeMemory()) / (1024*1024);
	}
}
