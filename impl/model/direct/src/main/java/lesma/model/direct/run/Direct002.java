package lesma.model.direct.run;

import jade.core.AID;

import java.util.List;

import lesma.annotations.TrustModel;
import openjade.core.OpenAgent;
import openjade.ontology.Rating;
import openjade.trust.ITrustModel;
import openjade.trust.model.Pair;

@TrustModel(name = "Ramdom - 141")
public class Direct002 implements ITrustModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIteration(int iteration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pair> getPairs(String[] terms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getValue(AID server) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAgent(OpenAgent taskAgent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rating> getRatings(AID aid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating addRating(AID client, AID server, int iteration, String term, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReliable(AID agent) {
		return (Math.random() >= 0.5001);
	}
	
}
