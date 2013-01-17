package fabricator.support

import fabricator.Fabricator
import fabricator.Factory
import fabricator.Property
import fabricator.properties.Association
import fabricator.properties.Dynamic
import fabricator.properties.SequenceProperty
import fabricator.properties.Static
import groovy.util.logging.Log

/**
 * Creates {@link Property} instances.
 * 
 * @author axprk
 */
@Log
class PropertyCreator {

	public Property create(String name, params) {
		log.info "trying to determine type of property, name=$name, params=$params"
		
		if(!params) {
			params = [name]
			log.info "params are empty, faking params to be $params"
		}
		
		def ignore = false
		
		def options = [:]
		if(params[0] instanceof Map) {
			options = params[0]

			log.info "first param is a map, using as options for association: $options"

			if(options.containsKey("ignore")) {
				ignore = options["ignore"]
				options.remove("ignore")
			}
			
			params = params.size() > 1 ? params[1..-1] : []
		}
		
		println "ignore = $ignore"
		
		def param = params.size() ? params[0] : name
		
		if(param instanceof Closure) {
			log.info "param is a closure, creating a dynamic property"
			return new Dynamic(name, ignore, param)
		}
		if(param instanceof Class) {
			log.info "param is a class, creating an association property"
			// association
			return createAssociation(name, ignore, Fabricator.configuration.factoryByClass(param), options)
		}
		if(param instanceof Sequence) {
			log.info "param is a sequence, creating a sequence property"
			// sequence
			return new SequenceProperty(name, ignore, param)
		}
		if(param instanceof String) {
			log.info "param is a string, check if something exists with this name"
			if(Fabricator.configuration.factoryByName(param)) {
				log.info "factory found for name $param"
				// it's an association
				return createAssociation(name, ignore, Fabricator.configuration.factoryByName(param), options)
			}
			if(Fabricator.configuration.sequenceByName(param)) {
				log.info "sequence found for name $param"
				// it's a sequence
				return new SequenceProperty(name, ignore, Fabricator.configuration.sequenceByName(param))
			}
		}

		log.info "param is static value, creating a static property"
		return new Static(name, ignore, param)
	}
	
	private Association createAssociation(String name, boolean ignore, Factory factory, Map options) {
		log.info "creating association: name=$name, factory=$factory, options=$options"
		
		def count = options.containsKey("count") ? options["count"] : 1
		
		// split off the overrides
		def overrides = new LinkedHashMap(options)
		overrides.remove("count")
		
		return new Association(name, ignore, factory, count, overrides)
	}

}
