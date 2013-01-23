package fabricator.dsl

import fabricator.DefinitionProxy
import fabricator.Fabricator
import fabricator.Sequence

class DSL {
	public static run(Closure closure) {
		DSL.with(closure)
	}
	
	public static void sequence(String name, Closure closure = null) {
		sequence(["start":1], name, closure)
	}
	
	public static void sequence(Map options, String name, Closure closure = null) {
		Fabricator.registerSequence(new Sequence(name, options, closure))
	}
	
	public static void constructor(Closure constructor) {
		Fabricator.configuration.constructor = constructor
	}
}
