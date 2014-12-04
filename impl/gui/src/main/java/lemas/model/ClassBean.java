package lemas.model;

@SuppressWarnings("rawtypes")
public class ClassBean implements Comparable<ClassBean> {

	private String name;

	private Class clazz;

	public ClassBean(String name, Class<?> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	@Override
	public int compareTo(ClassBean arg0) {
		return this.name.compareTo(arg0.name);
	}

	@Override
	public String toString() {
		return this.name;
	}

}
