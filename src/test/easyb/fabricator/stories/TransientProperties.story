package fabricator.stories

import fabricator.Fabricator;
import fabricator.Sequence
import fabricator.support.User

description "transient properties"

before "define a factory with a transient property", {
	Fabricator.define {
		factory(User) {
			ignore {
				emailHost "example.com"
			}
			
			first "John"
			last "Doe"
			email { "$first.$last@$emailHost" }
		}
	}
}

scenario "transient property, used in another dynamic property", {
	when "an object is created", {
		user = Fabricator.fabricate("user")
	}

	then "the transient property must be used in the dynamic property", {
		ensure(user) {
			has(first: "John")
			has(last: "Doe")
			has(email: "John.Doe@example.com")
		}
	}
}

scenario "transient property, used in another dynamic property; transient property is overridden in fabricate method", {
	when "an object is created", {
		user = Fabricator.fabricate("user", emailHost: "gmail.com")
	}

	then "the transient property must be used in the dynamic property", {
		ensure(user) {
			has(first: "John")
			has(last: "Doe")
			has(email: "John.Doe@gmail.com")
		}
	}
}