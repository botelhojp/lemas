package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "AHP", file = "contract_avg")
public class AHPMetrics extends AbstractIMetric {

	double valorTotal = 0;
	double media = 0;
	double contratos = 0;

	@Override
	public void preProcess(Instance instance) {
		// TODO Auto-generated method stub

	}

	@Override
	public double prosProcess(ACLMessage msg) {
		
		
		String confianca = msg.getContent().split(";")[2];
		System.out.println(confianca);
		return 0;
	}

}
