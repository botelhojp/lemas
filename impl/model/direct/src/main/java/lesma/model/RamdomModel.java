package lesma.model;

import lesma.annotations.TrustModel;

@TrustModel(name = "Ramdom Model")
public class RamdomModel extends AbstractModel {

	public RamdomModel() {
		properties.clear();
		properties.put("RELIABLE", "0.8");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Boolean test(openjade.ontology.Rating rating) {
		double r = Math.random();
		return (r <= getDouble("RELIABLE"));
	}
	
}
