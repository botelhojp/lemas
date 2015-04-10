package lemas.trust;

import jade.core.AID;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "None")
public class NoneModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void setTest(Rating rating) {
		super.setTest(rating);
		myAgent.test(rating.getServer());
	}
	
	@Override
	public String test(AID aid) {
		return "AGREE;" + test.getRound();		
	}
}
