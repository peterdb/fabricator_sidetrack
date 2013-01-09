package fabricator.properties

import java.util.logging.Level;

import fabricator.Fabricator
import fabricator.Factory
import fabricator.Property
import fabricator.Sequence
import groovy.util.logging.Log;

/**
 * Creates {@link Property} instances.
 * 
 * @author axprk
 */
@Log
class PropertyCreator {

	public Property create(String name, Object[] params) {
		log.info "trying to determine type of property, name=$name, params=$params"
		
		if(!params) {
			params = [name]
			log.info "params are empty, faking params to be $params"
		}
		
		def ignored = false
		
		def options = [:]
		if(params[0] instanceof Map) {
			options = params[0]

			log.info "first param is a map, using as options for association: $options"

			if(options.containsKey("ignore")) {
				ignored = options["ignore"]
				options.remove("ignore")
			}
			
			params = params.length > 1 ? params[1..-1] : []
		}
		
		println "ignored = $ignored"
		
		def param = params.length ? params[0] : name
		
		if(param instanceof Closure) {
			log.info "param is a closure, creating a dynamic property"
			return new Dynamic(name, ignored, param)
		}
		if(param instanceof Class) {
			log.info "param is a class, creating an association property"
			// association
			return createAssociation(name, ignored, Fabricator.factoryByClass(param), options)
		}
		if(param instanceof Sequence) {
			log.info "param is a sequence, creating a sequence property"
			// sequence
			return new Dynamic(name, ignored, { param.next() })
		}
		if(param instanceof String) {
			log.info "param is a string, check if something exists with this name"
			if(Fabricator.factoryByName(param)) {
				log.info "factory found for name $param"
				// it's an association
				return createAssociation(name, ignored, Fabricator.factoryByName(param), options)
			}
			if(Fabricator.sequenceByName(param)) {
				log.info "sequence found for name $param"
				// it's a sequence
				return new Dynamic(name, ignored, { Fabricator.sequenceByName(param).next() })
			}
		}

		log.info "param is static value, creating a static property"
		return new Static(name, ignored, param)
	}
	
	private Association createAssociation(String name, boolean ignored, Factory factory, Map options) {
		log.info "creating association: name=$name, factory=$factory, options=$options"
		
		def count = options.containsKey("count") ? options["count"] : 1
		
		// split off the overrides
		def overrides = new LinkedHashMap(options)
		overrides.remove("count")
		
		return new Association(name, ignored, factory, count, overrides)
	}

}
