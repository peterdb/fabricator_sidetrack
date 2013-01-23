package fabricator.stories

import fabricator.Fabricator;

import fabricator.support.User

scenario "create a blueprint", {
	given "a blueprint is created", {
		Fabricator.reset()
		
		User.blueprint {
			first "John"
			last "Doe"
			email { "$first.$last@example.com" }
		}
	}
	
	when "a user is buildd", {
		user = User.build()
	}

	then "the dynamic property must resolve the other properties", {
		ensure(user) {
			has(first: "John")
			has(last: "Doe")
			has(email: "John.Doe@example.com")
		}
	}
}

scenario "transient property, used in a dynamic property", {
	given "define a blueprint with a transient property", {
		Fabricator.reset()
		
		User.blueprint {
			ignore {
				emailHost "example.com"
			}
			
			first "John"
			last "Doe"
			email { "$first.$last@$emailHost" }
		}
	}

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
