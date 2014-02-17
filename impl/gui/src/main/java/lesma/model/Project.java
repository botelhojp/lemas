package lesma.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private String trustmodel;
	private String clazz;
	private String saveIn;
	private String arff;
	private String loading;
	private String host;
	private String conteiner;
	private boolean monitor;

	private List<Result> results;

	public Project() {
		results = new ArrayList<Result>();
		host = "-gui, -local-host, 127.0.0.1";
		conteiner = "-container, -container-name, Agents-Container";
		monitor = true;
		loading="1 :: seller:lesma.model.direct.agent.TaskAgent(Agents-Container,openjade.trust.DirectModel,TERRIBLE)\n" +
				"1 :: buyer:lesma.model.direct.agent.TaskAgent(Agents-Container,openjade.trust.DirectModel,TERRIBLE)";
		arff="%\n" +
				"% Creator..: Vanderson Botelho\n" +
				"% Date.....: Fevereiro, 2014\n" +
				"% \n" +
				"@RELATION iterations\n" +
				"\n" +
				"@ATTRIBUTE aidA		{agent_001, agent_002, agent_003, agent_004, agent_005, agent_006, agent_007, agent_008}\n" +
				"@ATTRIBUTE aidb		{agent_009, agent_010}\n" +
				"@ATTRIBUTE iteration	NUMERIC\n" +
				"@ATTRIBUTE context	{quality, price, delivery}\n" +
				"@ATTRIBUTE value	NUMERIC\n" +
				"\n" +
				"@DATA\n" +
				"'agent_001','agent_009','1','quality','0.9'\n" +
				"'agent_001','agent_009','1','price','0.5'\n" +
				"'agent_001','agent_009','1','delivery','0.1'\n" +
				"'agent_001','agent_010','1','quality','0.1'\n" +
				"'agent_001','agent_010','1','price','0.4'\n" +
				"'agent_001','agent_010','1','delivery','0.8";
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

}
