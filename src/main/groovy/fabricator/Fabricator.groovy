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
	
	public static define(Closure closure) {
		DSL.run(closure)
	}
	
	public static configure(Closure closure) {
		configuration.with(closure)
	}
	
	public static Object build(Map overrides = [:], String name) throws FabricatorException {
		assert name, "name cannot be empty"
		
		def blueprint = configuration.blueprintByName(name)
		
		if(!blueprint) {
			throw new FabricatorException("no blueprint found with name $name")
		}
		
		return new ObjectBuilder(blueprint).build(overrides)
	}
		
	public static Object generate(name) {
		return configuration.sequenceByName(name).next()
	}
	
	public static Map propertiesFor(String name) {
		return configuration.blueprintByName(name).runWith([:])
	}

	public static Map propertiesFor(Class klass) {
		def name = klass.simpleName.toLowerCase()
		
		return propertiesFor(name)
	}
	
	public static void reset() {
		configuration = new Configuration()
	}
	
	public static void reload() {
		reset()
		load()
	}
	
	public static void load() {
		BlueprintLoader.load(configuration.blueprintsPath)
	}
	
	// TODO automate these delegating methods
	public static void registerBlueprint(Blueprint blueprint) {
		configuration.registerBlueprint(blueprint)
	}
	
	public static Blueprint blueprintByName(String name) {
		return configuration.blueprintByName(name)
	}
	
	public static void registerSequence(Sequence sequence) {
		configuration.registerSequence(sequence)
	}
}
