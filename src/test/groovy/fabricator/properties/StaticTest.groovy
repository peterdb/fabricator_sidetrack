package fabricator.properties;

import static org.junit.Assert.*;

import org.junit.Test;

import spock.lang.Specification;

class StaticTest extends Specification {

	def construction() {
		when:
		def property = new Static("name", false, "value")
		
		then:
		assert "name" == property.name
		assert "value" == property.value
		assert !property.isIgnore()
	}

	def "toClosure returns closure that produces static value"() {
		setup:
		def property = new Static("name", false, "value")
		
		when:
		def closure = property.toClosure()
		
		then:
		assert closure
		assert "value" == closure()
	}

}
