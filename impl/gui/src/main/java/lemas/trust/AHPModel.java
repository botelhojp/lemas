package lemas.trust;

import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jade.core.AID;
import lemas.trust.metrics.Classes;
import lemas.trust.metrics.Clazz;
import lemas.trust.metrics.ahp.AHPConfig;
import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "AHP")
public class AHPModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private static AHPConfig pref = null;

	public AHPModel() {
		super();
		properties.put("PREFERENCIAS", "");
	}

	public static AHPConfig getConfig() {
		return pref;
	}

	@Override
	public void setTest(Rating rating) {
		if (pref == null) {
			try {
				Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setDateFormat(DateFormat.LONG).setPrettyPrinting().setVersion(1.0).create();
				Reader reader = new FileReader(properties.get("PREFERENCIAS").toString());
				pref = gson.fromJson(reader, AHPConfig.class);
				System.out.println(gson.toJson(pref));
			} catch (Exception ex) {
				throw new RuntimeException("erro", ex);
			}
		}
		super.setTest(rating);
		myAgent.test(rating.getServer());

	}

	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}

	@Override
	public String test(AID aid) {
		double sum = 0.0;
		double count = 0.0;
		for (Rating r : getRatings(aid)) {
			double delta = (r.getRound()) / (double) (test.getRound());
			count += delta;
			Clazz esperado = Classes.getClass(r.getValue());
			sum += (esperado.getValue() * delta);
		}
		addRating(test);
		if (count == 0) {
			count = 1;
		}
		return myAgent.getLocalName() + ";" + aid.getLocalName() + ";" + test.getRound() + ";" + (sum / count);
	}

}
