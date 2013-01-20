package fabricator

import fabricator.properties.Dynamic
import fabricator.properties.Static
import fabricator.support.Named
import fabricator.support.PropertyValueCollector

class Factory {

	final String name
	final List aliases
	final Class classToCreate
	final Factory parent

	@Delegate
	final Definition definition
	
	public Factory(String name, Map options = [:]) {
		this.name = name
		this.aliases = options["aliases"]
		this.parent = options["parent"]
		this.classToCreate = options["class"]
		
		definition = new Definition(name)
	}
	
	public Class getClassToCreate() {
		return classToCreate ? classToCreate : parent.getClassToCreate()
	}
	
	public Object run(Map<String, Object> overrides = [:]) {
		def result = runWith(null, overrides)
		
		// first call all default callbacks
		for (Closure afterCreate : Fabricator.configuration.afterCreateCallbacks) {
			def tmp = afterCreate.call(result)
			
			if(tmp != null) {
				result = tmp
			}
		}
		
		// then the factory specific ones
		
		return result
	}
	
	public Object runWith(Object target, Map<String, Object> overrides = [:]) {
		PropertyValueCollector collector = new PropertyValueCollector()
		
		def allProperties = getPropertiesToApply(overrides)
		
		allProperties.each { name, property ->
			Closure closure = property.toClosure()

			// set the delegate to result so dynamic properties can reference properties that already have been set
			def value = collector.with(closure)
			
			collector[name] = value
		}

		// create new instance if necessary
		if(target == null) {
			def closure = instantiator ? instantiator : Fabricator.configuration.instantiator
			
			// use the collector as a delegate, so property values are resolved in the instantiator closure
			closure.delegate = collector
			closure.resolveStrategy = Closure.DELEGATE_FIRST
			
			target = closure.call(getClassToCreate())			
		}
		
		// apply all non-transient property values in the collector to the target object
		collector.values.each { name, value ->
			// skip if ignored
			if(!allProperties[name].ignore) {
				target[name] = value
			}
		}
		
		return target;
	}
	
	private Map<String, Property> getPropertiesToApply(Map<String, Object> overrides) {
		// use a linked map, so the order of the properties is kept
		def props = new LinkedHashMap()
		
		// first collect all properties (mine + parent)
		collectProperties(props)

		if(overrides == null) {
			overrides = [:]
		}
				
		props.putAll(overrides.collectEntries{ name, override ->
			def ignore = props[name] ? props[name].ignore : false
			
			[name, override instanceof Closure ? new Dynamic(name, ignore, override) : new Static(name, ignore, override)]
		})
		
		return props
	}
	
	private void collectProperties(Map<String, Property> collected) {
		if(parent) {
			parent.collectProperties(collected)
		}
		
		collected.putAll(declarations.toProperties().collectEntries { property -> [(property.name): property]})
	}
	
	def instantiator(Closure instantiator) {
		assert instantiator != null
		
		this.instantiator = instantiator
	}
	
	def factory(Object[] args) {
		return Fabricator.configuration.factory(this, args)
	}
	
}
