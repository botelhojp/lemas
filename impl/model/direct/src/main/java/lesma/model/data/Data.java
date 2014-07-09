package lesma.model.data;

import openjade.core.DataProvider;
import openjade.ontology.RatingAttribute;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Data {

	private static Instances dt;

	public static Instance createByRating(jade.util.leap.List attributes) {
		Instances dt = getDataSet();
		Instance inst = new DenseInstance(dt.numAttributes());
		inst.setDataset(dt);
		for (int i = 0; i < attributes.size(); i++) {
			setValue(inst, dt.attribute(i), ((RatingAttribute) attributes.get(i)).getValue());
		}
		return inst;
	}

	private static void setValue(Instance inst, Attribute attribute, String string) {
		try {
			switch (attribute.type()) {
			case Attribute.NUMERIC:
				inst.setValue(attribute, Double.parseDouble(string));
				break;
			case Attribute.DATE:
				inst.setValue(attribute, attribute.parseDate(string));
				break;
			default:
				inst.setValue(attribute, string);
				break;
			}
		} catch (Exception e) {

		}
	}

	private static Instances getDataSet() {
		if (dt == null) {
			dt = (Instances) DataProvider.getInstance().get("DATASET");
		}
		return dt;
	}

}
