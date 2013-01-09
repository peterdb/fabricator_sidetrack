package fabricator

import fabricator.support.DefaultNamingStrategy
import fabricator.support.PropertyValueCollector
import fabricator.support.NamedRegistry
import fabricator.support.NamingStrategy

/**
 * Entry point
 * 
 * @author peter
 */
public class Fabricator {

	final static NamedRegistry<Sequence> sequences = new NamedRegistry<Sequence>()
	final static NamedRegistry<Factory> factories = new NamedRegistry<Factory>()

	static NamingStrategy naming = new DefaultNamingStrategy()
	
	public static void define(Closure closure) {
		Fabricator.with(closure)
	}
	
	// TODO try to convert the name to a valid class name
	public static Factory factory(Map options = [:], String name, Class klass, Closure closure) {
		return factory(options, name, klass, null, closure)
	}

	public static Factory factory(Map options = [:], String name, Class klass) {
		return factory(options, name, klass, null, null)
	}

	public static Factory factory(Map options = [:], String name, Class klass, Factory parent, Closure closure) {
		assert name, "name cannot be null or empty"
		assert klass, "klass cannot be null"

		def aliases = options["aliases"]
		if(aliases == null) {
			aliases = []
		}

		Factory factory = new Factory(name, aliases, klass, parent)
		if(closure) {
			factory.with(closure)
		}

		return registerFactory(factory)
	}
	
	public static Factory factory(Map options = [:], Class klass) {
		return factory(options, klass, null)
	}

	public static Factory factory(Map options = [:], Class klass, Closure closure) {
		def name = naming.nameFor(klass)
		
		return factory(options, name, klass, closure)
	}

	public static Factory factory(Map options = [:], String name, Closure closure = null) {
		def from = options["from"]
		def parent
		if(from instanceof String) {
			parent = factoryByName(from)
		} else if(from instanceof Class) {
			def parentName = naming.nameFor(from)

			parent = factoryByName(parentName)
		}

		assert parent, "no parent factory found for " + from

		return factory(name, parent.klass, parent, closure)
	}
	
	public static Factory factory(Factory parent = null, Object[] args = []) {
		Map options = args.find { it instanceof Map }
		if(options == null) {
			options = [:]
		}
		String name = args.find { it instanceof String }
		Closure closure = args.find { it instanceof Closure }
		Class klass = parent ? parent.klass : args.find { it instanceof Class }
		
		return factory(options, name, klass, parent, closure)
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
		def name = naming.nameFor(klass)

		return factoryByName(name)
	}

	public static Object fabricate(Map overrides = [:], String name) {
		return factoryByName(name).run(overrides)
	}

	public static Object fabricate(Map overrides = [:], Class klass) {
		def name = naming.nameFor(klass)

		return fabricate(overrides, name)
	}

	public static Sequence sequence() {
		return sequence([:], "default", 0, null)
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
	
	public static void reset(name) {
		sequenceByName(name).reset()
	}

	public static Object generate(name) {
		return sequenceByName(name).next()
	}
	
	public static Map propertiesFor(String name) {
		return factoryByName(name).runWith([:])
	}

	public static Map propertiesFor(Class klass) {
		def name = naming.nameFor(klass)
		
		return propertiesFor(name)
	}
}
