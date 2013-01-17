package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.Kitten
import fabricator.support.Post
import fabricator.support.User

description "aliases"

scenario "factory alias", {
	when "a factory is defined with aliases", {
		Fabricator.define {
			factory(User, aliases: ["author", "commenter"]) {
				first "John"
				last "Doe"
			}
		}
	}
	
	then "a lookup with the alias should return the factory", {
		Fabricator.configuration.with {  
			factoryByName("author").shouldBe factoryByName("user")
			factoryByName("commenter").shouldBe factoryByName("user")
		}
	}
}

scenario "sequence alias", {
	when "a sequene is factoryd with aliases", {
		Fabricator.define {  
			sequence("id", aliases: ["ids", "numeric_sequence"])
		}
	}
	
	then "a lookup with the alias should return the sequence", {
		Fabricator.configuration.with {  
			sequenceByName("ids").shouldBe sequenceByName("id")
			sequenceByName("numeric_sequence").shouldBe sequenceByName("id")
		}
	}
}