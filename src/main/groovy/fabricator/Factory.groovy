package fabricator

import fabricator.support.IgnoreBlockHandler
import fabricator.support.Named
import fabricator.support.PropertyCreator
import fabricator.support.PropertyValueCollector
import groovy.util.logging.Log

@Log
class Factory extends Named {

	final Class klass
	final Factory parent
	final Map<String, Property> properties = [:]

	Closure instantiator
	
	final PropertyCreator propertyCreator = new PropertyCreator()
	
	public Factory(String name, List aliases = [], Class klass, Factory parent = null) {
		super(name, aliases)
		
		this.klass = klass
		this.parent = parent;
	}
	
	public Object run(Map<String, Object> overrides = [:]) {
		return runWith(null, overrides)
	}
	
	public Object runWith(Object target, Map<String, Object> overrides = [:]) {
		PropertyValueCollector collector = new PropertyValueCollector()
		
		def allProperties = getPropertiesToApply(overrides)
		
		log.info "properties to apply to $target: $allProperties"
		
		allProperties.each { name, property ->
			Closure closure = property.toClosure()

			// set the delegate to result so dynamic properties can reference properties that already have been set
			log.info "evaluating property $name: $property"
			def value = collector.with(closure)
			
			log.info "value for property $name: $value"
			
			collector[name] = value
		}

		// create new instance if necessary
		if(target == null) {
			def closure = instantiator ? instantiator : Fabricator.configuration.instantiator
			
			// use the collector as a delegate, so property values are resolved in the instantiator closure
			closure.delegate = collector
			closure.resolveStrategy = Closure.DELEGATE_FIRST
			
			target = closure.call(klass)			
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
			println "override: $name, $override"
			def ignore = props[name] ? props[name].ignore : false
			println "ignore: $ignore"
			def overriddenProperty = propertyCreator.create(name, [["ignore": ignore], override])
			[name, overriddenProperty]
		})
		
		return props
	}
	
	private void collectProperties(Map<String, Property> collected) {
		if(parent) {
			parent.collectProperties(collected)
		}
		
		collected.putAll(properties)
	}
	
	def propertyMissing(String name) {
		Property property = propertyCreator.create(name, [])
		
		properties[name] = property
		
		return property
	}

	def methodMissing(String name, args) {
		Property property = propertyCreator.create(name, args)

		properties[name] = property;
		
		return property
	}
	
	def instantiator(Closure instantiator) {
		assert instantiator != null
		
		this.instantiator = instantiator
	}
	
	def factory(Object[] args) {
		return Fabricator.configuration.factory(this, args)
	}
	
	// for creating transient properties
	def ignore(Closure closure) {
		// route all property definitions through the transient property collector
		new IgnoreBlockHandler(this).with(closure)
	}
}
