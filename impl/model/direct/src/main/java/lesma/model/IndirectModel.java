package lesma.model;

import jade.core.AID;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;
import openjade.trust.WitnessUtil;

@TrustModel(name = "Indirect Model")
public class IndirectModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private static final String NEIGHBORS = "NEIGHBORS";
	private static final String FREQUENCY = "FREQUENCY";
	private double frequencyCount = 0;

	public IndirectModel() {
		properties.put(NEIGHBORS, "0.1");
		properties.put(FREQUENCY, "3");
	}

	@Override
	public void addRating(Rating rating) {
		
		super.addRating(rating);
		updateWitnesses();
		findReputation(rating.getServer());
	}

	public void findReputation(AID server) {
		for (AID witness : getWitnesses()) {
			myAgent.findReputation(witness, server);
		}
	}

	private void updateWitnesses() {
		try {
			if (++frequencyCount % getDouble(FREQUENCY) == 0) {
				List<AID> list = WitnessUtil.getWitness(getDouble(NEIGHBORS));
				Iterator<AID> it = list.iterator();
				while (it.hasNext()) {
					addWitness((AID) it.next());
				}
			}
		} catch (ConcurrentModificationException ex) {

		}
	}
}
