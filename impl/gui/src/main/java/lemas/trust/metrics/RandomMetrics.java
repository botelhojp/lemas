package lemas.trust.metrics;

import javax.swing.plaf.synth.SynthSeparatorUI;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Random")
public class RandomMetrics implements IMetrics {

	@Override
	public void preProcess(Instance instance) {

	}

	@Override
	public double prosProcess(ACLMessage msg) {
		return 30;
	}

}
