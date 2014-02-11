package lesma.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private String trustmodel;
	private String clazz;
	private String saveIn;
	private String arff;
	private String loading;
	private String ip;
	private String conteiner;
	private boolean monitor;

	private List<Result> results;

	public Project() {
		results = new ArrayList<Result>();
	}

	public String getTrustmodel() {
		return trustmodel;
	}

	public String getClazz() {
		return clazz;
	}

	public String getARFF() {
		return arff;
	}

	public void setTrustmodel(String trustmodel) {
		this.trustmodel = trustmodel;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setARFF(String file) {
		this.arff = file;
	}

	public List<Result> getResults() {
		return results;
	}

	public String getSaveIn() {
		return saveIn;
	}

	public void setSaveIn(String saveIn) {
		this.saveIn = saveIn;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public String getConteiner() {
		return conteiner;
	}

	public void setConteiner(String conteiner) {
		this.conteiner = conteiner;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public String getLoading() {
		return loading;
	}

	public void setLoading(String loading) {
		this.loading = loading;
	}

}
