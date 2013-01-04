package be.pinpoint.fabricator

import be.pinpoint.fabricator.support.User

scenario "create a factory", {
	given "a factory is created", {
		Fabricator.define(User) { 
			first "John"
			last "Doe"
			email { "$first.$last@gmail.com" }
		}

		user = Fabricator.fabricate(User)
	}
	
	then "it should have a name", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "John.Doe@gmail.com"
	}
}

scenario "sequence", {
	given "a factory is created", {
		Fabricator.sequence("email", { n -> "person$n@gmail.com" })
		
		Fabricator.define(User) {
			first "John"
			last "Doe"
			email { generate("email") }
		}

		user = Fabricator.fabricate(User, first: "overridden")
	}
	
	then "it should have a name", {
		user.first.shouldBe "overridden"
		user.email.shouldBe "person0@gmail.com"
	}
}