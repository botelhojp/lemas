package lemas.trust.metrics.ahp;

import java.util.List;

public class AHPFornecedor {

	private String id;

	private List<AHPPreferencia> propostas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<AHPPreferencia> getPropostas() {
		return propostas;
	}

	public void setPropostas(List<AHPPreferencia> propostas) {
		this.propostas = propostas;
	}

}
