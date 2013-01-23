package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.Kitten;
import fabricator.support.User

description "association properties"

scenario "an implicit assocation", {
	given "an association with no params", {
		Kitten.blueprint {
			name "Garfield"
		}
			
		User.blueprint {
			first "John"
			last "Doe"
			kitten
		}
	}
	
	when "a user is built", {
		user = User.build()
	}
	
	then "it should have a kitten", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "Garfield"
	}
}

scenario "an association with a custom blueprint", {
	given "an association with a custom blueprint", {
		Kitten.blueprint {
			name "generic kitten"
			
			blueprint("garfield") {
				name "Garfield"
			}
		}
		
		User.blueprint {
			first "John"
			last "Doe"
			kitten blueprint: "garfield" 
		}
	}
	
	when "a user is built", {
		user = User.build()
	}
	
	then "it should have garfield as a kitten", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "Garfield"
	}
}

scenario "an association with overridden property", {
	given "an association with an override", {
		Kitten.blueprint {
			name "generic kitten"
		}
		
		User.blueprint {
			first "John"
			last "Doe"
			kitten name:"Garfield"
		}
	}
	
	when "a user is built", {
		user = User.build()
	}
	
	then "it should have a kitten with an overridden name", {
		user.kitten.shouldNotBe null
		user.kitten.name.shouldBe "Garfield"
	}
}