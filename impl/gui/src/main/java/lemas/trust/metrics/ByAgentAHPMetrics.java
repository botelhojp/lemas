package lemas.trust.metrics;

import java.util.Enumeration;

import jade.lang.acl.ACLMessage;
import lemas.form.DialogResult;
import lemas.trust.AHPModel;
import lemas.trust.metrics.ahp.AHPCliente;
import lemas.trust.metrics.ahp.AHPConfig;
import lemas.trust.metrics.ahp.AHPFornecedor;
import lesma.annotations.Metrics;

@Metrics(name = "By Agent AHP", file = "by_agent_ahp")
public class ByAgentAHPMetrics extends ByAgentMetrics {
	
	

	@Override
	public double prosProcess(ACLMessage msg) {
		Fornecedor fe = getFornecedor(msg);
		String cliente = msg.getContent().split(";")[0];
		int round = Integer.parseInt(msg.getContent().split(";")[2]);
		String valor = msg.getContent().split(";")[3];
		calcAHP(cliente, fe, Double.parseDouble(valor));

		Enumeration<String> en = hash.keys();
		while (en.hasMoreElements()) {
			String type = en.nextElement();
			Fornecedor f = hash.get(type);
			DialogResult.getInstance().addResultForce(f.id, round, f.value);
		}
		// retorno nao utilizado
		return 0;
	}

	private void calcAHP(String cliente, Fornecedor _f, double trust) {
		AHPConfig c = AHPModel.getConfig();
		for(AHPCliente cli : c.clientes){
			if(cli.id.equals(cliente)){
				System.out.println(cli.id);
				break;
			}
		}
		for(AHPFornecedor f : c.fornecedores){
			if(f.id.equals(_f.nome)){
				System.out.println(f.id);
				break;
			}
		}
		_f.value = trust * 3;
	
	}

}
