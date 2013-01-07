package be.pinpoint.fabricator

import be.pinpoint.fabricator.properties.Association
import be.pinpoint.fabricator.properties.Dynamic
import be.pinpoint.fabricator.properties.Static


class Factory {

	final String name
	final Class klass
	final Factory parent
	final Map<String, Property> properties = [:]

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

			result.setProperty(name, closure.call())
		}

		return result;
	}
	
	private Map<String, Property> getPropertiesToApply(Map<String, Object> overrides) {
		// use a linked map, so the order of the properties is kept
		def props = new LinkedHashMap()
		
		// first collect all properties (mine + parent)
		collectProperties(props)
		
		props.putAll(overrides.collectEntries{ name, override ->
			[name, createProperty(name, override)]
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
		// this is a property -> check for association

		Property property = createProperty(name, [])
		
		properties[name] = property
		
		return property
	}

	def methodMissing(String name, args) {
		Property property = createProperty(name, args)

		properties[name] = property;
		
		return property
	}

	private Property createProperty(String name, Object[] params) {
		// name = attribute name
		// args: closure -> dynamic, value -> static, empty -> use name as param to find a sequence

		if(params.size() >= 1) {
			// check for association
			def param = params[0]
			if(param instanceof LinkedHashMap) {
				// association with count?
				if(param.size() == 1 && param.containsKey("count")) {
					Factory factory = Fabricator.factoryByName(name)
				
					if(factory) {
						// check if second param is a closure
						if(params.size() > 1 && params[1] instanceof Closure) {
							return new Association(name, factory, param["count"], params[1])
						} else {
							return new Association(name, factory, param["count"], null)
						}						
					}
				}
			}
		}
		
		if(params.size() == 1) {
			def param = params[0]
			if(param instanceof Closure) {
				return new Dynamic(name, param)
			}
	
			if(param instanceof Sequence) {
				return new Dynamic(name, { Fabricator.generate(param.name) })
			}
			
			if(param == null) {
				// association, use the name as lookup
				Factory factory = Fabricator.factoryByName(name)
				
				if(factory) {
					return new Association(name, factory)
				}else {
					// try with a sequence
					Sequence sequence = Fabricator.sequenceByName(name)
					if(sequence) {
						return new Dynamic(name, { Fabricator.generate(name) })
					}
				}
				// TODO handle no factory and no sequence found
			}
	
			return new Static(name, param)
		}
	}
}
