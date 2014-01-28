package lesma.model;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import openjade.core.annotation.TrustModel;

import org.reflections.Reflections;

import tesma.model.direc.agent.TaskAgent;

public class LesmaReflection {

	private static List<TrustModelBean> listTrustModel = new ArrayList<TrustModelBean>();

	public static List<TrustModelBean> loadTrusModel() {
		if (listTrustModel.isEmpty()) {
			Reflections reflections = new Reflections("");
			
			loadAll(reflections, Agent.class);
			
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TrustModel.class);
			for (Class<?> clazz : annotatedClasses) {
				TrustModel annotation = clazz.getAnnotation(TrustModel.class);
				listTrustModel.add(new TrustModelBean(annotation.name(), clazz));
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
