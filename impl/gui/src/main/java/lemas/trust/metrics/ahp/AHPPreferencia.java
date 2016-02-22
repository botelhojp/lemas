package lemas.trust.metrics.ahp;

public class AHPPreferencia {

	private String produto;
	private double valor_maximo;
	private double quantidade;
	private double prazo_entrega;
	private double prazo_pagamento;

	public String getProduto() {
		return produto;
	}

	public double getValor_maximo() {
		return valor_maximo;
	}

	public void setValor_maximo(double valor_maximo) {
		this.valor_maximo = valor_maximo;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getPrazo_entrega() {
		return prazo_entrega;
	}

	public void setPrazo_entrega(double prazo_entrega) {
		this.prazo_entrega = prazo_entrega;
	}

	public double getPrazo_pagamento() {
		return prazo_pagamento;
	}

	public void setPrazo_pagamento(double prazo_pagamento) {
		this.prazo_pagamento = prazo_pagamento;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}



}
