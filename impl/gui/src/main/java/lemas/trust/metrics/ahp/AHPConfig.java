package lemas.trust.metrics.ahp;

import java.util.ArrayList;
import java.util.List;

public class AHPConfig {

	public List<AHPCliente> clientes;
	public List<AHPFornecedor> fornecedores;

	public AHPConfig() {
		clientes = new ArrayList<AHPCliente>();
		fornecedores = new ArrayList<AHPFornecedor>();
	}
}
