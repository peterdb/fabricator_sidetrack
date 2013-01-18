package fabricator

import fabricator.configuration.Configuration
import fabricator.dsl.DSL

/**
 * Entry point
 * 
 * @author peter
 */
public class Fabricator {
	
	@Delegate
	static Configuration configuration = new Configuration()
	
	public static void define(Closure closure) {
		DSL.with(closure)
	}
	
	public static Object fabricate(Map overrides = [:], String name) {
		return configuration.factoryByName(name).run(overrides)
	}

	public static Object fabricate(Map overrides = [:], Class klass) {
		def name = configuration.naming.nameFor(klass)

		return fabricate(overrides, name)
	}

	public static Object generate(name) {
		return configuration.sequenceByName(name).next()
	}
	
	public static Map propertiesFor(String name) {
		return configuration.factoryByName(name).runWith([:])
	}

	public static Map propertiesFor(Class klass) {
		def name = configuration.naming.nameFor(klass)
		
		return propertiesFor(name)
	}
	
	public static void reload() {
		/* things to do:
		 * - reset configuration
		 * - reset custom strategies: instantiator, callbacks, ...
		 * - reset sequences?
		 * - reload definitions
		 */
		
		configuration = new Configuration()
	}
	
	// TODO automate these delegating methods
	public static void registerFactory(Factory factory) {
		configuration.registerFactory(factory)
	}
	
	public static Factory factoryByName(String name) {
		return configuration.factoryByName(name)
	}
	
	public static void registerSequence(Sequence sequence) {
		configuration.registerSequence(sequence)
	}
}
