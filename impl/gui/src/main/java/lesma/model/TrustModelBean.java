package lesma.model;

@SuppressWarnings("rawtypes")
public class TrustModelBean implements Comparable<TrustModelBean> {

	private String name;

	private Class clazz;

	public TrustModelBean(String name, Class<?> clazz) {
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
	public int compareTo(TrustModelBean arg0) {
		return this.name.compareTo(arg0.name);
	}

	@Override
	public String toString() {
		return this.name;
	}

}
