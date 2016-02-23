package lemas.trust.metrics;

import lemas.trust.AHPModel;
import lemas.trust.metrics.ahp.AHPCliente;
import lemas.trust.metrics.ahp.AHPConfig;
import lemas.trust.metrics.ahp.AHPFornecedor;
import lemas.trust.metrics.ahp.AHPPreferencia;
import lesma.annotations.Metrics;

@Metrics(name = "AHP - Risco+Trust", file = "ahp_risco_trust_value")
public class AHPTrustValueMetrics extends AHPRiskValueMetrics {

	@Override
	protected void calcAHP(String cliente, Fornecedor _f, double _trust) {
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

				double decisao = ahpMedia - (ahpMedia * (ajusteRisco / 100));
				
				decisao = (decisao + (decisao * _trust))/2;
				

				_f.value = decisao;
				return;
			}
		}
	}

}
