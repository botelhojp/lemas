package lemas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Project {

	private String trustmodel;
	private String clazz;
	private String saveIn;
	private String loading;
	private String host;
	private String conteiner;
	private boolean monitor;
	private Properties properties;

	private List<Result> results;

	public Project() {
		properties = new Properties();
		results = new ArrayList<Result>();
		host = "-gui, -local-host, 127.0.0.1";
		conteiner = "-container, -container-name, Agents-Container";
		monitor = true;
		loading="1 :: seller:lesma.model.direct.agent.TaskAgent(Agents-Container,openjade.trust.DirectModel,TERRIBLE)\n" +
				"1 :: buyer:lesma.model.direct.agent.TaskAgent(Agents-Container,openjade.trust.DirectModel,TERRIBLE)";
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

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
