package fabricator.support

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames=true)
@EqualsAndHashCode
class Company {

	final name
	def ticker

	def Company(String name) {
		println "in constructor with $name"
		
		assert name, "name is verplicht"
		
		this.name = name
	}
}
