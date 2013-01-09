package fabricator.support

import javax.xml.bind.GetPropertyAction;

class PropertyValueCollector {
	final Map values = [:]

	def propertyMissing(String name, value) {
		values[name] = value
	}
	
	def propertyMissing(String name) {
		// if not yet added as a property, fail
		if(!values.containsKey(name)) {
			throw new MissingPropertyException(name, PropertyValueCollector)
		}
		
		values[name]
	}
}
