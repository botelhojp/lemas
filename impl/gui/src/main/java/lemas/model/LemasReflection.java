package lemas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import lesma.annotations.Metrics;
import lesma.annotations.TrustModel;

public class LemasReflection {

	private static List<ClassBean> trustModels = new ArrayList<ClassBean>();
	
	private static List<ClassBean> metrics = new ArrayList<ClassBean>();
	
	public static List<ClassBean> getTrustModels() {
		if (trustModels.isEmpty()) {
			Reflections reflections = new Reflections("");
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TrustModel.class);
			for (Class<?> clazz : annotatedClasses) {
				TrustModel annotation = clazz.getAnnotation(TrustModel.class);
				trustModels.add(new ClassBean(annotation.name(), clazz));
			}
			Collections.sort(trustModels);
		}
		return trustModels;
	}

	public static List<ClassBean> getMetrics() {
		if (metrics.isEmpty()) {
			Reflections reflections = new Reflections("");
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Metrics.class);
			for (Class<?> clazz : annotatedClasses) {
				Metrics annotation = clazz.getAnnotation(Metrics.class);
				metrics.add(new ClassBean(annotation.name(), clazz));
			}
			Collections.sort(metrics);
		}
		return metrics;
	}

	
}
