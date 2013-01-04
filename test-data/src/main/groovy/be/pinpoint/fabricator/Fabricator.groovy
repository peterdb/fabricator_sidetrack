package be.pinpoint.fabricator

import org.apache.commons.lang.StringUtils

import be.pinpoint.fabricator.support.User

/**
 * Entry point
 * 
 * @author peter
 */
public class Fabricator {

	final static Map<String, Sequence> sequences = new HashMap<String, Sequence>()
	final static Map<String, Factory> factories = new HashMap<String, Factory>()

	// TODO try to convert the name to a valid class name
	// TODO factory aliases
	// TODO hierarchies
	public static Factory define(String name, Class klass, Closure closure = null) {
		return define(name, klass, null, closure)
	}
	
	public static Factory define(String name, Class klass, Factory parent, Closure closure = null) {
		assert name, "name cannot be null or empty"
		assert klass, "klass cannot be null"

		Factory factory = new Factory(name, klass, parent)
		if(closure) {
			closure.delegate = factory
			closure(factory)
		}

		return registerFactory(factory)
	}

	public static Factory define(Class klass, Closure closure = null) {
		use(StringUtils) {
			def name = klass.getSimpleName().uncapitalize()

			return define(name, klass, closure)
		}
	}

	public static Factory define(Map options, String name, Closure closure = null) {
		def from = options["from"]
		def parent
		if(from instanceof String) {
			parent = factoryByName(from)
		} else if(from instanceof Class) {
			use(StringUtils) {
				def parentName = from.getSimpleName().uncapitalize()

				parent = factoryByName(parentName)
			}
		}

		assert parent, "no parent factory found for " + from
		
		return define(name, parent.klass, parent, closure)
	}

	public static Factory registerFactory(Factory factory) {
		assert factory, "factory cannot be null"

		factories[factory.name] = factory

		return factory
	}

	public static Factory factoryByName(String name) {
		return factories[name]
	}

	public static Object fabricate(Map overrides = [:], String name) {
		return factoryByName(name).run(overrides)
	}

	public static Object fabricate(Map overrides = [:], Class klass) {
		use(StringUtils) {
			def name = klass.getSimpleName().uncapitalize()

			return fabricate(overrides, name)
		}
	}

	public static Sequence sequence(String name, Closure closure = null) {
		return sequence(name, 0, closure)
	}

	public static Sequence sequence(String name, Object value, Closure closure = null) {
		return registerSequence(new Sequence(name, value, closure))
	}

	public static Sequence registerSequence(Sequence sequence) {
		assert sequence, "sequence cannot be null"

		// TODO sequence aliases
		sequences[sequence.name] = sequence

		return sequence
	}

	public static Sequence sequenceByName(name) {
		assert name, "name cannot be null or empty"

		return sequences[name]
	}

	public static Object generate(name) {
		return sequenceByName(name).next()
	}
}
