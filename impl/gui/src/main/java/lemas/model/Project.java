package lemas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Project {

    private String trustmodel;
    private String clazz;
    private String metricsClass;
    private String saveIn;
    private String host;
    private String arff;
    private String conteiner;
    private boolean verLog;
    private boolean verResult;
    private boolean simulated;
    private boolean saveDB;
    private boolean step;
    private String delay;
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

    public boolean getVerLog() {
        return verLog;
    }

    public boolean getVerResult() {
        return verResult;
    }

    public void setVerLog(boolean verLog) {
        this.verLog = verLog;
    }

    public void setVerResult(boolean verResult) {
        this.verResult = verResult;
    }
    
    
    public boolean getSimulated() {
        return simulated;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }
    
    public boolean getSaveDB() {
        return saveDB;
    }

    public void setSaveDB(boolean saveDB) {
        this.saveDB = saveDB;
    }

	public boolean isStep() {
		return step;
	}

	public void setStep(boolean step) {
		this.step = step;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getArff() {
		return arff;
	}

	public void setArff(String arff) {
		this.arff = arff;
	}
    
}
