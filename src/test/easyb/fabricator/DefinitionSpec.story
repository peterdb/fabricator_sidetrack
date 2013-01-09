package fabricator

import fabricator.Sequence
import fabricator.support.User

scenario "define a factory", {
	given "a factory is defined", {
		Fabricator.define {
			factory(User)
		}
	}

	then "it registers a factory with the name", {
		Fabricator.factoryByName("user").shouldBeAFactory
	}
}


scenario "define a sequence", {
	given "a sequence is defined", {
		Fabricator.define {
			sequence("seq")
		}
	}

	then "it registers a sequence with the name", {
		// stupid naming collision with groovy.lang.Sequence
		Fabricator.sequenceByName("seq").class.shouldBe Sequence
	}
}
