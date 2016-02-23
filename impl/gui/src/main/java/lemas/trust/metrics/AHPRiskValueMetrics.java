package lemas.trust.metrics;

import java.util.Enumeration;

import jade.lang.acl.ACLMessage;
import lemas.form.DialogResult;
import lemas.trust.AHPModel;
import lemas.trust.metrics.ahp.AHPCliente;
import lemas.trust.metrics.ahp.AHPConfig;
import lemas.trust.metrics.ahp.AHPFornecedor;
import lemas.trust.metrics.ahp.AHPPreferencia;
import lesma.annotations.Metrics;

@Metrics(name = "AHP - Risco", file = "ahp_risco_value")
public class AHPRiskValueMetrics extends AHPAgentMetrics {

	@Override
	public double prosProcess(ACLMessage msg) {
		Fornecedor fe = getFornecedor(msg);
		String cliente = msg.getContent().split(";")[0];
		int round = Integer.parseInt(msg.getContent().split(";")[2]);
		String valor = msg.getContent().split(";")[3];
		calcAHP(cliente, fe, Double.parseDouble(valor));

		Enumeration<String> en = fornecedores.keys();
		while (en.hasMoreElements()) {
			String type = en.nextElement();
			Fornecedor f = fornecedores.get(type);
			DialogResult.getInstance().addResultForce(f.id, round, f.value);
		}
		// retorno nao utilizado
		return 0;
	}

	protected void calcAHP(String cliente, Fornecedor _f, double trust) {
		AHPConfig c = AHPModel.getConfig();
		AHPCliente cli = c.clientes.get(0);

		for (AHPFornecedor f : c.fornecedores) {
			if (f.getId().equals(_f.nome)) {
				System.out.println(f.getId());
				AHPPreferencia proposta = f.getPropostas().get(0);

				double notaValorMax = nota_f1(cli.getPreferencias().get(0).getValor_maximo(), proposta.getValor_maximo());
				double notaQuantidade = nota_f2(cli.getPreferencias().get(0).getQuantidade(), proposta.getQuantidade());
				double notaEntrega = nota_f1(cli.getPreferencias().get(0).getPrazo_entrega(), proposta.getPrazo_entrega());
				double notaPrazoPagamento = nota_f3(cli.getPreferencias().get(0).getPrazo_pagamento(), proposta.getPrazo_pagamento());

				double notaMinima = min(notaValorMax, notaQuantidade, notaEntrega, notaPrazoPagamento);
				double notaMaxima = max(notaValorMax, notaQuantidade, notaEntrega, notaPrazoPagamento);

				double ahpValorMaximo = (cli.getAhp().get(0).getValor_maximo() / 100) * notaValorMax;
				double ahpQuantidade = (cli.getAhp().get(0).getQuantidade() / 100) * notaQuantidade;
				double aphEntrega = (cli.getAhp().get(0).getPrazo_entrega() / 100) * notaEntrega;
				double aphPrazoPagamento = (cli.getAhp().get(0).getPrazo_pagamento() / 100) * notaPrazoPagamento;

				double ahpMedia = soma(ahpValorMaximo, ahpQuantidade, aphEntrega, aphPrazoPagamento);
				double notaMedia = (notaMinima + notaMaxima) / 2.0;

				String br = br(ahpMedia, notaMedia);
				double compBR = comparacao(notaMedia, ahpMedia, notaMinima);

				double fatorDeAjuste = fatorAjuste(br, compBR);

				double ajusteRisco = (fatorDeAjuste * ahpMedia) / 100;
				

				double decisao = ahpMedia - (ahpMedia*(ajusteRisco/100));

				_f.value = decisao;
				return;
			}
		}
	}

	public double fatorAjuste(String br, double compBR) {
		if (br.equals("R=B")) {
			return compBR * 100;
		}
		if (br.equals("B/R")) {
			return (1 / compBR) * 100;
		}
		if (br.equals("R/B")) {
			return compBR * 100;
		}
		throw new RuntimeException("valor invalido");
	}

	public String br(double ahpMedia, double notaMedia) {
		return (ahpMedia == notaMedia) ? "R=B" : (ahpMedia > notaMedia) ? "B/R" : "R/B";
	}

	public double comparacao(double iteracao, double ahp, double min) {
		if (iteracao == ahp) {
			return 1.0;
		}
		if (ahp > iteracao) {
			return 1 / (min / ahp);
		} else {
			return (min / ahp) + 1;
		}
	}

	public double soma(double... values) {
		double soma = 0.0;
		for (double value : values) {
			soma += value;
		}
		return soma;
	}

	public double min(double... values) {
		if (values == null || values.length == 0) {
			throw new RuntimeException("lista vazia");
		}
		double min = Double.MAX_VALUE;
		for (double value : values) {
			if (value <= min) {
				min = value;
			}
		}
		return min;
	}

	public double max(double... values) {
		if (values == null || values.length == 0) {
			throw new RuntimeException("lista vazia");
		}
		double max = Double.MIN_VALUE;
		for (double value : values) {
			if (value >= max) {
				max = value;
			}
		}
		return max;
	}

	public double nota_f3(double pref, double prop) {
		double y = 50;
		double x = 20;
		if (prop >= 50) {
			return 10.0;
		}
		if (prop <= 20) {
			return 0.0;
		}
		return (prop - x) / ((y - x) / 10);
	}

	public double nota_f1(double pref, double prop) {
		if (prop <= (pref / 2)) {
			return 10;
		} else if (prop > (pref * 2)) {
			return 0;
		} else {
			return ((20 * prop) - (40 * pref)) / (-3 * pref);
		}
	}

	public double nota_f2(double pref, double prop) {
		return ((prop * 100) / pref) / 10;
	}
}
