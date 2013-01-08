package fabricator

import fabricator.Fabricator;
import fabricator.support.Kitten
import fabricator.support.Post
import fabricator.support.User

description "association stories"

scenario "association with only count", {
	given "a user def with a posts association"
	when "a user is fabricated"
	then "it should have 5 posts"
}

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