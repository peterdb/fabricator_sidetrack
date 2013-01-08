package fabricator

import org.apache.commons.lang.StringUtils

import fabricator.support.NamedRegistry

/**
 * Entry point
 * 
 * @author peter
 */
public class Fabricator {

	final static NamedRegistry<Sequence> sequences = new NamedRegistry<Sequence>()
	final static NamedRegistry<Factory> factories = new NamedRegistry<Factory>()

	// TODO try to convert the name to a valid class name
	// TODO factory aliases
	// TODO hierarchies
	public static Factory define(Map options = [:], String name, Class klass, Closure closure) {
		return define(options, name, klass, null, closure)
	}

	public static Factory define(Map options = [:], String name, Class klass) {
		return define(options, name, klass, null, null)
	}

	public static Factory define(Map options = [:], String name, Class klass, Factory parent, Closure closure) {
		assert name, "name cannot be null or empty"
		assert klass, "klass cannot be null"

		def aliases = options["aliases"]
		if(aliases == null) {
			aliases = []
		}

		Factory factory = new Factory(name, aliases, klass, parent)
		if(closure) {
			closure.delegate = factory
			closure(factory)
		}

		return registerFactory(factory)
	}

	public static Factory define(Map options = [:], Class klass) {
		return define(options, klass, null)
	}

	public static Factory define(Map options = [:], Class klass, Closure closure) {
		use(StringUtils) {
			def name = klass.getSimpleName().uncapitalize()

			return define(options, name, klass, closure)
		}
	}

	public static Factory define(Map options = [:], String name, Closure closure = null) {
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

		factories.register(factory)

		return factory
	}

	public static Factory factoryByName(String name) {
		return factories[name]
	}

	public static Factory factoryByClass(Class klass) {
		use(StringUtils) {
			def name = klass.getSimpleName().uncapitalize()

			return factoryByName(name)
		}
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
		return sequence([:], name, 0, closure)
	}

	public static Sequence sequence(Map options, String name, Closure closure = null) {
		return sequence(options, name, 0, closure)
	}

	public static Sequence sequence(String name, Object value, Closure closure = null) {
		return sequence([:], name, value, closure)
	}

	public static Sequence sequence(Map options, String name, Object value, Closure closure = null) {
		def aliases = options["aliases"]
		if(aliases == null) {
			aliases = []
		}

		return registerSequence(new Sequence(name, aliases, value, closure))
	}

	public static Sequence registerSequence(Sequence sequence) {
		assert sequence, "sequence cannot be null"

		sequences.register(sequence)

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
