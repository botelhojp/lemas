package lemas.trust.metrics;

import java.util.Enumeration;
import java.util.Hashtable;

import org.jfree.data.xy.XYSeries;

import jade.lang.acl.ACLMessage;
import lemas.form.DialogResult;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "AHP - Apenas Confiança", file = "ahp_agent_value")
public class AHPAgentMetrics extends AbstractIMetric {

	protected Hashtable<String, Fornecedor> fornecedores = new Hashtable<String, AHPAgentMetrics.Fornecedor>();
	protected DialogResult dl = null;

	@Override
	public void preProcess(Instance instance) {
		dl = DialogResult.getInstance();
		dl.disabledAddResult();
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.nome = instance.toString(1);
		if (!fornecedores.containsKey(fornecedor.nome)) {
			fornecedores.put(fornecedor.nome, fornecedor);
			DialogResult.getInstance().getXSerie().addSeries(new XYSeries(fornecedor.nome));
			fornecedor.id = dl.getXSerie().getSeriesCount()-1;
		}
	}

	@Override
	public double prosProcess(ACLMessage msg) {
		int round = Integer.parseInt(msg.getContent().split(";")[2]);
		String valor = msg.getContent().split(";")[3];		
		getFornecedor(msg).value = Double.parseDouble(valor);
		
		Enumeration<String> en = fornecedores.keys();
		while (en.hasMoreElements()) {
			String type = en.nextElement();
			Fornecedor f = fornecedores.get(type);
			DialogResult.getInstance().addResultForce(f.id, round, f.value);
		}
		//retorno nao utilizado
		return 0;
	}

	protected Fornecedor getFornecedor(ACLMessage msg) {
		String fornecedor = msg.getContent().split(";")[1];
		return fornecedores.get(fornecedor);
	}

	protected class Fornecedor {
		int id;
		String nome;
		double value;
	}

}
