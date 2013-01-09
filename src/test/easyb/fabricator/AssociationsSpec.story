package fabricator

import fabricator.Fabricator;
import fabricator.support.Kitten;
import fabricator.support.User

description "associations"

scenario "a simple association", {
	given "an association with no params", {
		Fabricator.define {
			Fabricator.factory(Kitten) {
				name "Garfield"
			}
			
			Fabricator.factory(User) {
				first "John"
				last "Doe"
				kitten
			}
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have a kitten", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "Garfield"
	}
}

scenario "an association with overridden property", {
	given "a user factory with a kitten association", {
		Fabricator.define {
			Fabricator.factory(Kitten) {
				name "Garfield"
			}
			Fabricator.factory(User) {
				first "John"
				last "Doe"
				kitten name:"overridden name"
			}
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have a kitten with an overridden name", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "overridden name"
	}
}