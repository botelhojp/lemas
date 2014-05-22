package lemas.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.util.ArrayUtilities;

@SuppressWarnings("all")
public class Runner {
	
	public static Project currentProject;

	public static void run(Project project) {
		try {
			currentProject = project;
			Class c = Class.forName(project.getClazz());
			Method m = c.getMethod("main", String[].class);
			
			ArrayList<String> main = makeArray(project.getHost(), ",");			
			if (project.isMonitor()){
				main.add("-gui");
			}
			//String[] main = {"-gui", "-local-host", "127.0.0.1"};
			//m.invoke(null, (Object) main);
			//String[] agent01 = { "-local-host", "127.0.0.1", "-container", "-container-name", "Agents-Container", "agent_001:lesma.model.direct.agent.TaskAgent(Agents-Container,openjade.trust.DirectModel,TERRIBLE)" };
			
			
			m.invoke(null, (Object) main.toArray(new String[0]));			
			String[] loader = { "-local-host", "127.0.0.1", "-container", "-container-name", "Agents-Container", "lemas_loader:lemas.agent.LoaderAgent(param01, param02, param03)"};
			m.invoke(null, (Object) loader);
			
			
//			ArrayList<String> host = makeArray(project.getHost(), ",");	
//			ArrayList<String> conteiner = makeArray(project.getConteiner(), ",");
//			ArrayList<String> agents = makeAgents(project.getLoading()); 
//			for (String agent: agents) {
//				m.invoke(null, (Object) concat(host, conteiner, agent));
//			}		
//			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

	private static String[] concat(ArrayList<String> hosts, ArrayList<String> conteiners, String agent) {
		ArrayList<String> r = new ArrayList<String>();
		for(String host: hosts){
			r.add(host);
		}
		for(String conteiner: conteiners){
			r.add(conteiner);
		}
		r.add(agent);
		return r.toArray(new String[0]);
		
	}

	private static ArrayList<String> makeAgents(String loading) {
		ArrayList<String> agents = new ArrayList<String>();
		String[] lines = loading.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] tokens = line.split("::");
			if (tokens.length == 2){
				Long num = Long.parseLong(tokens[0].trim());
				for(int k = 1; k <= num; k++){
					String name = find("([^\"]+):", tokens[1].trim());
					String clazz = find(":([^\"]+)", tokens[1].trim());
					agents.add(name + "_" + k + ":" + clazz);
				}
			}			
		}
		
		return agents;
	}

	private static String find(String _pattern, String value) {
		Pattern pattern = Pattern.compile(_pattern);
		Matcher m = pattern.matcher(value);
		while (m.find()) {
		    return m.group(1);		    
		}
		return null;
	}

	private static ArrayList<String> makeArray(String value, String split) {
		ArrayList<String> r = new ArrayList<String>();
		String[] retur = value.trim().split(split);
		for (int i = 0; i < retur.length; i++) {
			r.add(retur[i].trim());
		}		
		return r;
	}

}
