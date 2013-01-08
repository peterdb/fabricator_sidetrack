package fabricator

import fabricator.Fabricator;
import fabricator.support.Kitten;
import fabricator.support.User

description "association stories"

scenario "a simple association", {
	given "a user factory with a simple kitten association", {
		Fabricator.define(Kitten) {
			name "Garfield"
		}
		Fabricator.define(User) { 
			first "John"
			last "Doe"
			kitten // simple association, with no additional configuration
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
		Fabricator.define(Kitten) {
			name "Garfield"
		}
		Fabricator.define(User) {
			first "John"
			last "Doe"
			kitten name:"overridden name"
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

scenario "bla bla lba", {
	given "a user factory with a kitten association", {
		Fabricator.define(Kitten) {
			name "Garfield"
		}
		Fabricator.define(User) {
			first "John"
			last "Doe"
			kitten { Fabricator.fabricate Kitten }
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have a kitten with an overridden name", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "Garfield"
	}
}