package fabricator.properties

import fabricator.Fabricator
import fabricator.Factory
import fabricator.Property

/**
 * Creates {@link Property} instances.
 * 
 * @author axprk
 */
class PropertyCreator {

	public Property create(String name, Object[] params) {
		if(!params) {
			params = [name]
		}
		
		//assert params && params.size() in [1,2], "creator needs 1 or 2 params"
		
		def options = [:]
		if(params[0] instanceof Map) {
			// first params should be a map
			assert params[0] instanceof Map, "if two params are given, first param must be a map with options"
			
			options = params[0]
			params = params.length > 1 ? params[1..-1] : []
		}
		
		def param = params.length ? params[0] : name
		
		if(param instanceof Closure) {
			return new Dynamic(name, param)
		}
		if(param instanceof Class) {
			// association
			return createAssociation(name, Fabricator.factoryByClass(param), options)
		}
		if(param instanceof Sequence) {
			// sequence
			return new SequenceProperty(name, param)
		}
		if(param instanceof String) {
			if(Fabricator.factoryByName(param)) {
				// it's an association
				return createAssociation(name, Fabricator.factoryByName(param), options)
			}
			if(Fabricator.sequenceByName(param)) {
				// it's a sequence
				return new SequenceProperty(name, Fabricator.sequenceByName(param))
			}
		}
		
		return new Static(name, param)
	}
	
	private Association createAssociation(String name, Factory factory, Map options) {
		def count = options.containsKey("count") ? options["count"] : 1
		
		// split off the overrides
		def overrides = new LinkedHashMap(options)
		overrides.remove("count")
		
		return new Association(name, factory, count, overrides)
	}

}
