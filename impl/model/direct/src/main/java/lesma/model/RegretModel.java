package lesma.model;

import jade.core.AID;

import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;
import openjade.trust.Reliable;

@TrustModel(name = "Regret Model")
public class RegretModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}

	@Override
	public Reliable isReliable(AID agent) {
		List<Rating> list = ratings.get(agent);
		if (list == null) {
			return Reliable.UNCERTAIN;
		} else {
			float sum = 0;
			for (Rating r : list) {
				sum += r.getValue();
			}
			if (sum > 0) {
				return Reliable.YES;
			} else {
				return Reliable.NO;
			}
		}
	}

}
