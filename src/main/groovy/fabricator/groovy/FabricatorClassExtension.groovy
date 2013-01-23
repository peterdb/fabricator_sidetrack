package fabricator.groovy

import fabricator.Blueprint
import fabricator.Fabricator
import fabricator.dsl.BlueprintMethods

/**
 * Extend {@link Class} with <code>blueprint</code>, <code>build</code> and <code>create</code> methods.
 * 
 * @author peter
 */
@Category(Class)
class FabricatorClassExtension {
	public Blueprint blueprint(Closure closure = null) {
		blueprint([:], closure)
	}
	
	public Blueprint blueprint(Map options, Closure closure = null) {
		// this is a "default" blueprint, so register the class
		if(!options.containsKey("class")) {
			options["class"] = this
		}
		
		blueprint(options, this.simpleName.toLowerCase(), closure)
	}

	public Blueprint blueprint(Map options, String name, Closure closure = null) {
		if(!name) {
			name = this.simpleName.toLowerCase()
		}
		
		// if no parent defined, register the default factory for this class
		if(!options.containsKey("parent")) {
			options["parent"] = Fabricator.blueprintByName(this.simpleName.toLowerCase())
		}
		
		BlueprintMethods.blueprint(name, options, closure)
	}
	
	public Blueprint blueprint(String name, Closure closure = null) {
		blueprint([:], name, closure)
	}

	public Object build(String name, Map overrides = [:]) {
		Fabricator.build(overrides, name)
	}

	public Object build(Map overrides = [:]) {
		Fabricator.build(overrides, this.simpleName.toLowerCase())
	}
}
