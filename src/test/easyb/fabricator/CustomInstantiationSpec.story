package fabricator

import fabricator.support.User

description "factories can be passed an instantiator closure will use that to instantiate the objects"

scenario "factory with an instantiator", {
	given "a factory is defined with an instantiator", {
		Fabricator.define() {
			factory(User) {
				instantiateWith { return new User(email: "email.from.instantiator@example.com") }
				
				first "John"
				last "Doe"
			}
		}
	}
	
	when "an object is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should be created through the instantiator", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "email.from.instantiator@example.com"
	}
}