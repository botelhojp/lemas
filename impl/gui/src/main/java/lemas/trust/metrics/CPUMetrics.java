package lemas.trust.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "CPU Load Average", file = "cpu_load_average")
public class CPUMetrics implements IMetrics {

	private OperatingSystemMXBean operatingSystemMXBean;
	
	public void preProcess(Instance instance) {
	}
	
	public CPUMetrics(){
		operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
		}
	}

	public double prosProcess(ACLMessage msg) {		
		return operatingSystemMXBean.getSystemLoadAverage();
	}
}
