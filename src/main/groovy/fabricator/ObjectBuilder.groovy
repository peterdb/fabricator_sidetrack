package fabricator

import fabricator.properties.Dynamic
import fabricator.properties.Static
import fabricator.support.PropertyValueCollector

class ObjectBuilder {

	final Blueprint blueprint
	
	public ObjectBuilder(Blueprint blueprint) {
		assert blueprint, "blueprint cannot be null"
		
		this.blueprint = blueprint
	}
	
	public Object build(Map overrides = [:]) {
		PropertyValueCollector collector = new PropertyValueCollector()

		def properties = getPropertiesToApply(overrides)

		evaluateProperties(properties, collector)

		def target = instantiateObject(collector)

		populateObject(target, collector, properties)

		return target;
	}
	
	private void evaluateProperties(Map properties, PropertyValueCollector collector) {
		properties.each { name, property ->
			Closure closure = property.toClosure()

			// set the delegate to result so dynamic properties can reference properties that already have been set
			def value = collector.with(closure)

			collector[name] = value
		}
	}
	
	private Object instantiateObject(PropertyValueCollector collector) {
		def constructor = blueprint.constructor ?: Fabricator.configuration.constructor
		
		// use the collector as a delegate, so property values are resolved in the constructor closure
		constructor.delegate = collector
		constructor.resolveStrategy = Closure.DELEGATE_FIRST

		return constructor(blueprint.getClassToCreate())
	}
	
	private void populateObject(Object target, PropertyValueCollector collector, Map properties) {
		// apply all non-transient property values in the collector to the target object
		collector.values.each { name, value ->
			if(!properties[name].ignore) {
				// TODO ignore primitive values when value == null
				target[name] = value
			}
		}
	}

	private Map<String, Property> getPropertiesToApply(Map<String, Object> overrides) {
		// use a linked map, so the order of the properties is kept
		def props = new LinkedHashMap()

		// first collect all properties
		props.putAll(blueprint.properties)

		// then add overrides
		if(overrides) {
			props.putAll(overrides.collectEntries{ name, override ->
				def ignore = props[name] ? props[name].ignore : false

				[name, override instanceof Closure ? new Dynamic(name, ignore, override) : new Static(name, ignore, override)]
			})
		}

		return props
	}
}
