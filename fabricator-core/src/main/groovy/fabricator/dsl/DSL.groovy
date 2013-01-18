package fabricator.dsl

import fabricator.DefinitionProxy
import fabricator.Fabricator
import fabricator.Sequence
import fabricator.Factory

class DSL {
	public static void factory(name, Map options = [:], Closure closure) {
		if(name instanceof Class) {
			if(!options["class"]) {
				options["class"] = name
			}

			name = name.simpleName.toLowerCase()
		}

		if(options["parent"] instanceof String) {
			options["parent"] = Fabricator.factoryByName(options["parent"])
		}

		Factory f = new Factory(name, options)
		DefinitionProxy proxy = new DefinitionProxy(f.definition)
		if(closure) {
			proxy.with(closure)
		}

		Fabricator.registerFactory(f)

		proxy.childFactories.each { childName, childOptions, childClosure ->
			if(!childOptions["parent"]) {
				childOptions["parent"] = name
			}

			factory(childName, childOptions, childClosure)
		}
	}
	
	public static void sequence(String name, Closure closure = null) {
		sequence(["start":1], name, closure)
	}
	
	public static void sequence(Map options, String name, Closure closure = null) {
		Fabricator.registerSequence(new Sequence(name, options, closure))
	}
}
