package fabricator

import fabricator.Fabricator;
import fabricator.support.User

scenario "create a sequence", {
	given "a sequence is created", {
		sequence = Fabricator.sequence("seq")
	}

	then "it should have a name", {
		sequence.name.shouldBe("seq")
	}
	
	and "it must be found by its name", {
		Fabricator.sequenceByName("seq").shouldBe sequence
	}
	
	and "it can be generated", {
		Fabricator.generate("seq").shouldBe 0
	}
}

scenario "create a factory", {
	given "a factory is created", {
		factory = Fabricator.factory(User)
	}

	then "it should have a name", {
		factory.name.shouldBe("user")
	}
	
	and "it should have a class", {
		factory.klass.shouldBe(User)
	}
	
	and "it must be found by its name", {
		Fabricator.factoryByName("user").shouldBe factory
	}
}