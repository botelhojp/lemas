package lemas.trust.metrics;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import lemas.util.Message;
import weka.core.Attribute;
import weka.core.Instances;

public class Classes {
	
	public static Classes instance;
	
	private static final double min = -1.0;
	private static final double max = 1.0;
	private static List<Clazz> list;
	private static Hashtable<String, Clazz> hash;
	
	private Classes(){}

	private Classes(Instances data) {
		list = new ArrayList<Clazz>();
		hash = new Hashtable<String, Clazz>();
		Attribute at = data.attribute(data.numAttributes() - 1);		
		double range = (max - min)/at.numValues();		
		double step = (max - min)/(at.numValues()-1);
		for(int i = 0; i < at.numValues(); i++){
			double r_max = max - (i*range);
			double r_min = r_max - range;
			int index = at.numValues() - i - 1;
			Clazz clazz = new Clazz(at.value(i), step*index - max, r_min, r_max);
			Message.message(clazz.toString());
			list.add(clazz);
			hash.put(clazz.getName(), clazz);
		}
		
	}

	public static Classes getInstance(Instances data) {
		if (instance == null){
			instance = new Classes(data);
		}
		return instance;
	}
	
	public static Clazz getClass(String key){
		return hash.get(key);
	}

	public static Clazz getClass(double value) {
		for (Clazz c : list){
			if (value >= c.getR_min() && value <= c.getR_max()){
				return c;
			}
		}
		return null;
	}
	
	public static  List<Clazz> getClasses(){
		return list;
	}

}
