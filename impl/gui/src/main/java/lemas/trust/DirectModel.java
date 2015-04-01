package lemas.trust;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Direct Model")
public class DirectModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void setTest(Rating rating) {
		super.setTest(rating);
		myAgent.test(rating.getServer());
	}
}
