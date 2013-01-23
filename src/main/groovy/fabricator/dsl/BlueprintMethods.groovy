package fabricator.dsl

import fabricator.Blueprint
import fabricator.DefinitionProxy
import fabricator.Fabricator

class BlueprintMethods {
	public static Blueprint blueprint(String name, Map options, Closure closure) {
		assert name, "name cannot be empty"
		assert options != null, "options cannot be null"
		
		// replace string ref to Blueprint
		if(options["parent"] instanceof String) {
			options["parent"] = Fabricator.blueprintByName(options["parent"])
		}

		Blueprint blueprint = new Blueprint(name, options)
		DefinitionProxy proxy = new DefinitionProxy(blueprint.definition)
		if(closure) {
			proxy.with(closure)
		}

		Fabricator.registerBlueprint(blueprint)

		proxy.childBlueprints.each { childName, childOptions, childClosure ->
			if(!childOptions["parent"]) {
				childOptions["parent"] = blueprint
			}

			BlueprintMethods.blueprint(childName, childOptions, childClosure)
		}
		
		return blueprint
	}
}
