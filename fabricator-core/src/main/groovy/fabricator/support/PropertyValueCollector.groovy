package fabricator.support

import javax.xml.bind.GetPropertyAction;

/**
 * Collect the values to populate an object
 * 
 * @author peter
 */
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
		
		if(name == "sequence") {
			new Exception("qmdklmqskdfjl").printStackTrace()
		}
		
		values[name]
	}
}
