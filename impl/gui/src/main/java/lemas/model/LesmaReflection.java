package lemas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lesma.annotations.TrustModel;

import org.reflections.Reflections;

public class LesmaReflection {

	private static List<TrustModelBean> trustModels = new ArrayList<TrustModelBean>();

	public static List<TrustModelBean> getTrustModels() {
		if (trustModels.isEmpty()) {
			Reflections reflections = new Reflections("");
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TrustModel.class);
			for (Class<?> clazz : annotatedClasses) {
				TrustModel annotation = clazz.getAnnotation(TrustModel.class);
				trustModels.add(new TrustModelBean(annotation.name(), clazz));
			}
			Collections.sort(trustModels);
		}
		return trustModels;
	}
}
