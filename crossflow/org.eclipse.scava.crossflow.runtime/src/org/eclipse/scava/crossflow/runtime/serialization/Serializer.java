package org.eclipse.scava.crossflow.runtime.serialization;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * {@link Serializer}s perform de/serialization operations on objects that are
 * intended to be used as payloads in {@link Stream}s.
 */
public interface Serializer {

	/**
	 * Initialise the {@link Serializer}.
	 * <p>
	 * Implementations should implement this method if any setup is required that
	 * cannot be safely performed in the constructor
	 */
	default public void init() {
	}

	/**
	 * Register a type that can be used with this {@link Serializer}.
	 * <p>
	 * This method can be used by implementations to perform any necessary setup
	 * required to serialize specific types i.e. register a custom serialization
	 * delegate for the given type.
	 * <p>
	 * The instance of {@link Serializer} is returned to emulate a fluent API
	 * 
	 * @param clazz the type to register
	 * @return This serializer for fluent API
	 */
	public Serializer registerType(Class<?> clazz);

	/**
	 * Retrieve the types registered in this {@link Serializer} i.e. all types
	 * registered using {@link #registerType(Class)}.
	 * 
	 * @return all types registered to this {@link Serializer}
	 */
	public Collection<Class<?>> getRegisteredTypes();

	/**
	 * Serialize the given input to a <code>String</code> representation
	 * 
	 * @param <O>   type of the input object
	 * @param input the object to serialize
	 * @return serialized input as a <code>String</code>
	 */
	public <O> String serialize(O input);

	/**
	 * Deserialize the given input from a <code>String</code> representation
	 * 
	 * @param <O>   type of the deserialized object
	 * @param input the string to deserialize
	 * @return the deserialized object of type <code>O</code>
	 */
	public <O> O deserialize(String input);

}