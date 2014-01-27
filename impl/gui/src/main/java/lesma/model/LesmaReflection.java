package lesma.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import openjade.core.annotation.TrustModel;

import org.reflections.Reflections;

public class LesmaReflection {

	private static List<TrustModelBean> listTrustModel = new ArrayList<TrustModelBean>();

	public static List<TrustModelBean> loadTrusModel() {
		if (listTrustModel.isEmpty()) {
			Reflections reflections = new Reflections("");
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TrustModel.class);
			for (Class<?> clazz : annotatedClasses) {
				TrustModel annotation = clazz.getAnnotation(TrustModel.class);
				listTrustModel.add(new TrustModelBean(annotation.name(), clazz));
			}
			Collections.sort(listTrustModel);
		}
		return listTrustModel;
	}

}
