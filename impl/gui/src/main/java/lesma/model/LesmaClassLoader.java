package lesma.model;

import java.beans.IntrospectionException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Set;

import openjade.core.annotation.TrustModel;

import org.reflections.Reflections;

public class LesmaClassLoader extends ClassLoader {

	public LesmaClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class loadClass(String name) throws ClassNotFoundException {

		try {
			String url = "file:/home/03397040477/.lesma/trustmodel/lesma-model-direct-1.0.0-SNAPSHOT.jar";
			URL myUrl = new URL(url);
			URLConnection connection = myUrl.openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();

			while (data != -1) {
				buffer.write(data);
				data = input.read();
			}

			input.close();

			byte[] classData = buffer.toByteArray();

			return defineClass(name, classData, 0, classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void load() {
		try {

			URL url = new URL("file://home/03397040477/workspace-open-jade/lesma/impl/model/direct/target/lesma-model-direct-1.0.0-SNAPSHOT.jar");

			Reflections reflections = new Reflections("");

			addURLToSystemClassLoader(url);

			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TrustModel.class);
			for (Class<?> clazz : annotatedClasses) {
				TrustModel annotation = clazz.getAnnotation(TrustModel.class);
				System.out.println(annotation.name());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addURLToSystemClassLoader(URL url) throws IntrospectionException {
		URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> classLoaderClass = URLClassLoader.class;
		try {
			Method method = classLoaderClass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(systemClassLoader, new Object[] { url });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IntrospectionException("Error when adding url to system ClassLoader ");
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		ClassLoader parentClassLoader = LesmaClassLoader.class.getClassLoader();
		LesmaClassLoader classLoader = new LesmaClassLoader(parentClassLoader);
		Class myObjectClass = classLoader.loadClass("tesma.model.direc.agent.SignerTaskAgent");

		

		// create new class loader so classes can be reloaded.
		classLoader = new LesmaClassLoader(parentClassLoader);
		myObjectClass = classLoader.loadClass("reflection.MyObject");

		

	}

}
