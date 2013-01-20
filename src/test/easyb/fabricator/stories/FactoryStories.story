package fabricator.stories

import fabricator.Fabricator;

import fabricator.support.User

after_each "rest Fabricator", { Fabricator.reload() }

scenario "create a factory", {
	given "a factory is created", {
		Fabricator.define {
			factory(User) {
				first "John"
				last "Doe"
				email { "$first.$last@gmail.com" }
			}
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}

	then "it should be created with the correct values", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "John.Doe@gmail.com"
	}
}