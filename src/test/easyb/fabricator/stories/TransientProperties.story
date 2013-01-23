package fabricator.stories

import fabricator.Fabricator;
import fabricator.Sequence
import fabricator.support.User

description "Transient (or ignored) properties can be used, but will be ignored when populating the object"

before "define a blueprint with a transient property", {
	User.blueprint {
		ignore {
			emailHost "example.com"
		}
		
		first "John"
		last "Doe"
		email { "$first.$last@$emailHost" }
	}
}

scenario "transient property, used in another dynamic property", {
	when "an object is built", {
		user = User.build()
	}

	then "the transient property must be used in the dynamic property", {
		ensure(user) {
			has(first: "John")
			has(last: "Doe")
			has(email: "John.Doe@example.com")
		}
	}
}

scenario "transient property, used in another dynamic property; transient property is overridden in build method", {
	when "an object is created", {
		user = User.build(emailHost: "gmail.com")
	}

	then "the transient property must be used in the dynamic property", {
		ensure(user) {
			has(first: "John")
			has(last: "Doe")
			has(email: "John.Doe@gmail.com")
		}
	}
}