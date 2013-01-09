package fabricator

import fabricator.support.Kitten
import fabricator.support.User

description "propertiesFor specification"

before "create factories", {
	given "a kitten and a user factory", {
		Fabricator.factory(Kitten) {
			name "Garfield"
		}
		 
		Fabricator.factory(User) {
			first "John"
			last { "Doe" }
			email Fabricator.sequence("email", { n -> "person$n@example.com" })
			kitten
		}
	}
}

scenario "calling Fabricator.propertiesFor", {
	given "properties for user", {
		properties = Fabricator.propertiesFor("user")
	}

	then "it returns a map of properties", {
		properties.size().shouldBe 4

		properties.first.shouldBe "John"
		properties.last.shouldBe "Doe"
		properties.email.shouldBe "person0@example.com"
		properties.kitten.name.shouldBe "Garfield"
	}
}
