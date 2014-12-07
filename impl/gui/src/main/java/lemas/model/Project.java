package lemas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Project {

	private String trustmodel;
	private String clazz;
	private String metricsClass;
	private String saveIn;
	private String loading;
	private String host;
	private String conteiner;
	private Properties properties;

	private List<Result> results;

	public Project() {
		properties = new Properties();
		results = new ArrayList<Result>();
		host = "-local-host, 127.0.0.1";
		conteiner = "-container, -container-name, Agents-Container";
	}
	
	public String getTrustmodel() {
		return trustmodel;
	}

	public String getClazz() {
		return clazz;
	}

	public void setTrustmodel(String trustmodel) {
		this.trustmodel = trustmodel;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
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

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public String getConteiner() {
		return conteiner;
	}

	public void setConteiner(String conteiner) {
		this.conteiner = conteiner;
	}

	public String getLoading() {
		return loading;
	}

	public void setLoading(String loading) {
		this.loading = loading;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getMetricsClass() {
		return metricsClass;
	}

	public void setMetricsClass(String metricsClass) {
		this.metricsClass = metricsClass;
	}

}
