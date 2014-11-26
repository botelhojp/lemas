package lemas.trust;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lesma.annotations.TrustModel;

@TrustModel(name = "Ramdom Model")
public class RamdomModel extends AbstractModel {

	public RamdomModel() {
		properties.clear();
		properties.put("RELIABLE", "0.8");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Boolean test(AID aid) {
		double r = Math.random();
		return (r <= getDouble("RELIABLE"));
	}
	
	public void findReputation(AID server) {
		myAgent.sendMessage(myAgent.getAID(), ACLMessage.REQUEST, "GET_DOSSIE", "");
	}
	
}
