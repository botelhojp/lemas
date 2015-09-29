package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import openjade.ontology.RatingAttribute;
import lemas.Lemas;
import weka.core.Instance;

public abstract class AbstractIMetric implements IMetrics {

	protected static Hashtable<Integer, List<String>> csv = new Hashtable<Integer, List<String>>();
	protected static int count = -1;

	public AbstractIMetric() {
		Lemas.seIMetrics(this);
		csv.put(++count, new ArrayList<String>());
		csv.get(count).add("model_" + count);
	}

	public abstract void preProcess(Instance instance);

	public abstract double prosProcess(ACLMessage msg);

	public double put(double value) {
		csv.get(count).add(""+value);
		return value;
	}
	
	protected RatingAttribute getAttributes(String key, jade.util.leap.List list) {
		for (int i = 0; i < list.size(); i++) {
			RatingAttribute ra = (RatingAttribute)list.get(i);
			if (ra.getName().equals(key))
				return ra;
		}
		return null;
	}

	public void save(File file) {
		if (file.exists()) {
			file.delete();
		}

		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			int colums = count+1;
			int rols = csv.get(0).size();

			for (int r = 0; r < rols; r++) {
				String line = "";
				for (int c = 0; c < colums; c++) {
					line += "\"" + csv.get(c).get(r).replace('.', ',') + "\";";
				}
				line = line.substring(0, line.length()-1);
				writer.println(line);
			}
			writer.close();
			csv.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
