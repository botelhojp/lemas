package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;

import java.io.File;

import weka.core.Instance;

public interface IMetrics {
	
	public void preProcess(Instance instance);

	public double prosProcess(ACLMessage msg);
	
	public void save(File cvs);

}
