package fabricator

import fabricator.Fabricator;
import fabricator.support.Kitten
import fabricator.support.Post
import fabricator.support.User

description "aliases"

scenario "factory alias", {
	when "a factory is factoryd with aliases", {
		Fabricator.factory(User, aliases: ["author", "commenter"]) {
			first "John"
			last "Doe"
		}
	}
	
	then "a lookup with the alias should return the factory", {
		Fabricator.factoryByName("author").shouldBe Fabricator.factoryByName("user")
		Fabricator.factoryByName("commenter").shouldBe Fabricator.factoryByName("user")
	}
}

scenario "sequence alias", {
	when "a sequene is factoryd with aliases", {
		Fabricator.sequence("id", aliases: ["ids", "numeric_sequence"])
	}
	
	then "a lookup with the alias should return the sequence", {
		Fabricator.sequenceByName("ids").shouldBe Fabricator.sequenceByName("id")
		Fabricator.sequenceByName("numeric_sequence").shouldBe Fabricator.sequenceByName("id")
	}
}