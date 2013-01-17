package fabricator

import fabricator.configuration.Configuration
import fabricator.support.DefaultNamingStrategy
import fabricator.support.NamedRegistry
import fabricator.support.NamingStrategy

/**
 * Entry point
 * 
 * @author peter
 */
public class Fabricator {

	static Configuration configuration = new Configuration()
	
	public static void define(Closure closure) {
		configuration.with(closure)
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
}
