package lemas.trust.metrics.ahp;

import java.util.List;

public class AHPCliente {

	private String id;

	private List<AHPPreferencia> preferencias;

	private List<AHPPreferencia> ahp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<AHPPreferencia> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<AHPPreferencia> preferencias) {
		this.preferencias = preferencias;
	}

	public List<AHPPreferencia> getAhp() {
		return ahp;
	}

	public void setAhp(List<AHPPreferencia> ahp) {
		this.ahp = ahp;
	}

}
