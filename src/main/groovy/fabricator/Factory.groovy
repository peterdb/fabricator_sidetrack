package fabricator

import fabricator.properties.PropertyCreator
import fabricator.support.Named
import fabricator.support.PropertyValueCollector

class Factory extends Named {

	final Class klass
	final Factory parent
	final Map<String, Property> properties = [:]

	Closure instantiator = { klass.newInstance() }
	
	final PropertyCreator propertyCreator = new PropertyCreator()
	
	public Factory(String name, List aliases = [], Class klass, Factory parent = null) {
		super(name, aliases)
		
		this.klass = klass
		this.parent = parent;
	}

	public Object run(Map<String, Object> overrides = [:]) {
		// TODO handle classes without default constructor
		def result = instantiator.call()

		runWith(result, overrides)
		
		return result
	}
	
	public Object runWith(Object target, Map<String, Object> overrides = [:]) {
		PropertyValueCollector collector = new PropertyValueCollector()
		
		def allProperties = getPropertiesToApply(overrides)
		allProperties.each { name, property ->
			Closure closure = property.toClosure()

			// set the delegate to result so dynamic properties can reference properties that already have been set
			closure.delegate = target
			closure.resolveStrategy = Closure.DELEGATE_FIRST

			collector[name] = closure.call()
			
			println "$name - ${collector[name]}"
		}
		
		println collector.values
		
		collector.values.each { name, value ->
			// skip if ignored
			if(!allProperties[name].ignored) {
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
			[name, propertyCreator.create(name, override)]
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
		Property property = propertyCreator.create(name)
		
		properties[name] = property
		
		return property
	}

	def methodMissing(String name, args) {
		Property property = propertyCreator.create(name, args)

		properties[name] = property;
		
		return property
	}
	
	def instantiateWith(Closure closure) {
		assert closure != null
		
		this.instantiator = closure
	}
	
	def factory(Object[] args) {
		return Fabricator.factory(this, args)
	}
	
	// for creating transient properties
	def ignore(Closure closure) {
		new TTT(this).with(closure)
	}
}
