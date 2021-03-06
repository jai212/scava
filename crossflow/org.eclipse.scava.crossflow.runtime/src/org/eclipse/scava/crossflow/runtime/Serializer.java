package org.eclipse.scava.crossflow.runtime;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Serializer {

	protected XStream xstream = new XStream(new DomDriver());

	public String toString(Object object) {
		return xstream.toXML(object);
	}

	@SuppressWarnings("unchecked")
	public <O> O toObject(String string) {
		return (O) xstream.fromXML(string);
	}

	@SuppressWarnings("unchecked")
	public <O> O toObject(File file) {
		return (O) xstream.fromXML(file);
	}

	public void setClassloader(ClassLoader classLoader) {
		xstream.setClassLoader(classLoader);
	}

	/**
	 * Register a Class to be aliased by this serializer.
	 * <p>
	 * Aliased classed will use their simple name for serialisation rather than
	 * their fully qualified names. i.e.
	 * <code>org.eclipse.scava.crossflow.runtime.Job</code> becomes <code>Job</code>
	 * 
	 * @param clazz The class to register
	 */
	public void register(Class<?> clazz) {
		xstream.alias(clazz.getSimpleName(), clazz);
	}

}
