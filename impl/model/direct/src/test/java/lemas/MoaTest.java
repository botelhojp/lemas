package lemas;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class MoaTest {

	private static Instance makeInstance() {
		try {

			Attribute client = new Attribute("client");
			Attribute server = new Attribute("server");
			Attribute date = new Attribute("date", "dd/MM/yyyy");
			
			
			Attribute comments = new Attribute("comments", (FastVector) null);
			Attribute item = new Attribute("item", (FastVector) null);
			Attribute price = new Attribute("price", Attribute.NUMERIC);

			List<String> feedback_values = new ArrayList<String>(3);
			feedback_values.add("pos");
			feedback_values.add("neg");
			feedback_values.add("neu");
			Attribute feedback = new Attribute("feedback", feedback_values);

			ArrayList<Attribute> attributes = new ArrayList<Attribute>(7);
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

			inst.setValue(race.attribute("client"), 53041511);
			inst.setValue(race.attribute("server"), 45323847);

			inst.setValue(race.attribute("date"), race.attribute("date").parseDate("01/01/2013"));

			inst.setValue(race.attribute("comments"), "boa");
			inst.setValue(race.attribute("item"), "xbox");
			inst.setValue(race.attribute("price"), 10.0);
			inst.setValue(race.attribute("feedback"), "pos");

			return inst;
		} catch (ParseException e) {
			throw new RuntimeException("");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(makeInstance());
	}

}
