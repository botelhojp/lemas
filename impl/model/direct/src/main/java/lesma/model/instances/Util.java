package lesma.model.instances;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Util {

	private static Instance makeInstance() {
		try {

			Attribute client = new Attribute("client", Attribute.NUMERIC);
			Attribute server = new Attribute("server", Attribute.NUMERIC);
			Attribute date = new Attribute("date", "dd/MM/yyyy");
			Attribute comments = new Attribute("comments");
			Attribute item = new Attribute("item");
			Attribute price = new Attribute("price", Attribute.NUMERIC);

			List<String> feedback_values = new ArrayList<String>(3);
			feedback_values.add("pos");
			feedback_values.add("neg");
			feedback_values.add("neu");
			Attribute feedback = new Attribute("feedback", feedback_values);

			ArrayList<Attribute> attributes = new ArrayList<Attribute>(3);
			attributes.add(client);
			attributes.add(server);
			attributes.add(date);
			attributes.add(comments);
			attributes.add(item);
			attributes.add(price);
			attributes.add(feedback);

			// Create the empty dataset "race" with above attributes
			Instances race = new Instances("rating", attributes, 0);

			Instance inst = new DenseInstance(7);
			inst.setDataset(race);

			inst.setValue(race.attribute(0), 53041511);
			inst.setValue(race.attribute(1), 45323847);

			inst.setValue(race.attribute(2), race.attribute(2).parseDate("01/01/2013"));

			inst.setValue(race.attribute(3), "boa");
			inst.setValue(race.attribute(4), "vamos lá");
			inst.setValue(race.attribute(5), 10.0);
			inst.setValue(race.attribute(6), "pos");

			return inst;
		} catch (ParseException e) {
			throw new RuntimeException("");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(makeInstance());
	}

}
