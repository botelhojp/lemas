package lemas.trust.metrics;

import java.util.Enumeration;

import jade.lang.acl.ACLMessage;
import lemas.form.DialogResult;
import lesma.annotations.Metrics;

@Metrics(name = "By Agent AHP", file = "by_agent_ahp")
public class ByAgentAHPMetrics extends ByAgentMetrics {

	@Override
	public double prosProcess(ACLMessage msg) {
		Fornecedor fe = getFornecedor(msg);
		int round = Integer.parseInt(msg.getContent().split(";")[1]);
		String valor = msg.getContent().split(";")[2];
		calcAHP(fe, Double.parseDouble(valor));

		Enumeration<String> en = hash.keys();
		while (en.hasMoreElements()) {
			String type = en.nextElement();
			Fornecedor f = hash.get(type);
			DialogResult.getInstance().addResultForce(f.id, round, f.value);
		}
		// retorno nao utilizado
		return 0;
	}

	private void calcAHP(Fornecedor f, double trust) {
		f.value = trust * 3;
	}

}
