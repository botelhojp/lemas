package lesma.model;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lesma.annotations.StartSMA;

import org.reflections.Reflections;

public class LesmaReflection {

	private static List<TrustModelBean> listTrustModel = new ArrayList<TrustModelBean>();

	public static List<TrustModelBean> loadTrusModel() {
		if (listTrustModel.isEmpty()) {
			Reflections reflections = new Reflections("");
			
			loadAll(reflections, Agent.class);
			
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(StartSMA.class);
			for (Class<?> clazz : annotatedClasses) {
				StartSMA annotation = clazz.getAnnotation(StartSMA.class);
				listTrustModel.add(new TrustModelBean(annotation.trustmodel(), clazz));
			}
			Collections.sort(listTrustModel);
		}
		return listTrustModel;
	}

	private static void loadAll(Reflections reflections, Class class1) {
		Set<Class<?>> subTypes = reflections.getSubTypesOf(class1);
		for (Class<?> clazz : subTypes) {
			listTrustModel.add(new TrustModelBean(clazz.getSimpleName(), clazz));
			loadAll(reflections, clazz);
		}
	}

}
