package fabricator

import fabricator.Fabricator;
import fabricator.support.User

scenario "create a factory", {
	given "a factory is created", {
		Fabricator.factory(User) { 
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
	given "an email sequence definition", {
		Fabricator.sequence("email", { n -> "person$n@gmail.com" })
		
		Fabricator.factory(User) {
			first "John"
			last "Doe"
			email { Fabricator.generate("email") }
		}
	}
	
	when " a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have a generated email", {
		user.email.shouldBe "person0@gmail.com"
	}
}