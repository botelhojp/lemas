package lemas.agent;

import java.util.Hashtable;

public class AgentCache {
	
	private static Hashtable<String, AgentOO> cache = new Hashtable<String, AgentOO>();

	public static void add(String localName, AgentOO agentOO) {
		cache.put(localName, agentOO);
	}

	public static AgentOO get(String localName) {
		return cache.get(localName);
	}

}
