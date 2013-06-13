package fabricator;

import static org.junit.Assert.*;

import org.junit.Test;

import spock.lang.Specification;

import fabricator.Sequence;

class SequenceTest extends Specification {

//	given "a sequence with a name", {
//		Fabricator.define {
//			sequence("seq")
//		}
//	}
//
//	then "it starts with 0", {
//		Fabricator.generate("seq").shouldBe 1
//	}
//
//	then "it increments by one with each call", {
//		Fabricator.generate("seq").shouldBe 2
//		Fabricator.generate("seq").shouldBe 3
//		Fabricator.generate("seq").shouldBe 4
//		Fabricator.generate("seq").shouldBe 5
//	}
	
	def "simple sequence should start with 0"() {
		setup: "a sequence with a name"
		def sequence = new Sequence("seq")
		
		when:
		def value = sequence.next()
		
		then: 
		assert value == 0
	}
	
}
