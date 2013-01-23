package fabricator.stories

import fabricator.Fabricator;
import fabricator.Sequence
import fabricator.support.User

description "sequence properties"

scenario "transient property, used in another dynamic property", {
	given "an implicit sequence property declaration", {
		Fabricator.define {
			sequence "first", { n -> "John$n" }
		}
		
		User.blueprint {
			ignore {
				emailHost "example.com"
			}
			
			first
			last "Doe"
			email { "$first.$last@$emailHost" }
		}
	}

	when "an object is built", {
		user1 = User.build()
		user2 = User.build()
	}

	then "the sequence property is correctly filled", {
		ensure(user1) {
			has(first: "John1")
			has(last: "Doe")
			has(email: "John1.Doe@example.com")
		}
		ensure(user2) {
			has(first: "John2")
			has(last: "Doe")
			has(email: "John2.Doe@example.com")
		}
	}
}