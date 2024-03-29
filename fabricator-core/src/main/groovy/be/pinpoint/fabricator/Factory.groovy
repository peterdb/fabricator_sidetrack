package be.pinpoint.fabricator

import be.pinpoint.fabricator.properties.Association
import be.pinpoint.fabricator.properties.Dynamic
import be.pinpoint.fabricator.properties.PropertyCreator
import be.pinpoint.fabricator.properties.Static


class Factory {

	final String name
	final Class klass
	final Factory parent
	final Map<String, Property> properties = [:]

	final PropertyCreator propertyCreator = new PropertyCreator()
	
	public Factory(String name, Class klass, Factory parent = null) {
		this.name = name
		this.klass = klass
		this.parent = parent;
	}

	public Object run(Map<String, Object> overrides = [:]) {
		// TODO handle classes without default constructor
		def result = klass.newInstance()

		getPropertiesToApply(overrides).each { name, property ->
			Closure closure = property.toClosure()

			// set the delegate to result so dynamic properties can reference properties that already have been set
			closure.delegate = result
			closure.resolveStrategy = Closure.DELEGATE_FIRST

			result[name] = closure.call()
		}

		return result;
	}
	
	private Map<String, Property> getPropertiesToApply(Map<String, Object> overrides) {
		// use a linked map, so the order of the properties is kept
		def props = new LinkedHashMap()
		
		// first collect all properties (mine + parent)
		collectProperties(props)
		
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
}
