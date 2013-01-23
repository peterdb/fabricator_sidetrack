package fabricator

import fabricator.properties.Dynamic
import fabricator.properties.Static
import fabricator.support.PropertyValueCollector

class Blueprint {

	final String name
	final List aliases
	final Class classToCreate
	final Blueprint parent

	@Delegate
	final Definition definition
	
	public Blueprint(String name, Map options = [:]) {
		this.name = name
		this.aliases = options["aliases"] ?: []
		this.parent = options["parent"]
		this.classToCreate = options["class"]
		
		definition = new Definition()
	}
	
	public List getNames() {
		return [name] + aliases
	}
	
	public Class getClassToCreate() {
		return classToCreate ? classToCreate : parent.getClassToCreate()
	}
	
	public Closure getConstructor() {
		if(definition.constructor) {
			return definition.constructor
		}
		
		if(parent) {
			return parent.constructor
		}
		
		return Fabricator.configuration.constructor
	}
	
	public Map getProperties() {
		Map properties = new LinkedHashMap()
		
		if(parent) {
			properties.putAll(parent.properties)
		}

		definition.properties.each { property ->
			properties.put(property.name, property)
		} 
		
		return properties
	}
	
}
