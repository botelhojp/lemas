package lemas.trust.metrics;

public class Clazz {
	
	private String name;
	private double value;
	private double r_min;
	private double r_max;

	public Clazz(String name, double value, double r_min, double r_max) {
		this.name = name; 
		this.value = value;
		this.r_min = r_min;
		this.r_max = r_max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getR_min() {
		return r_min;
	}

	public void setR_min(double r_min) {
		this.r_min = r_min;
	}

	public double getR_max() {
		return r_max;
	}

	public void setR_max(double r_max) {
		this.r_max = r_max;
	}
	
	public double diff(Clazz other){
		return this.getValue() - other.getValue();
	}
	
	@Override
	public String toString() {		
		return "(name="+  name + " value=" + value + " min=" + r_min + " max=" + r_max+")";
	}
}
