package fabricator

import fabricator.Sequence
import fabricator.support.User

description "transient properties"

scenario "define a factory", {
	given "a factory is defined", {
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

	then "it registers a factory with the name", {
		println Fabricator.fabricate("user") 
	}
}