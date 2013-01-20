package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.Kitten
import fabricator.support.User

description "propertiesFor specification"


scenario "propertiesFor must return map with all evaluated properties", {
	given "create factories", {
		Fabricator.define {
			factory(Kitten) {
				name "Garfield"
			}
			
			sequence("first", { n -> "John$n" })
	
			factory(User) {
				ignore {
					host "example.com"
				}
				
				first
				last "Doe"
				email { "$first.$last@$host" }
				kitten
			}
		}
	}
	
	when "propertiesFor is called", {
		properties = Fabricator.propertiesFor("user")
	}
	
	then "it must contain the properties", {
		properties.first.shouldBe "John1"
		properties.last.shouldBe "Doe"
		properties.email.shouldBe "John1.Doe@example.com"
		properties.kitten.name.shouldBe "Garfield"
	}
	
	then "it must exclude transient properties", {
		assert properties.containsKey("host") == false
	}
}