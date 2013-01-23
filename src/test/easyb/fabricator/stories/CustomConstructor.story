package fabricator.stories

import fabricator.Fabricator
import fabricator.support.Company
import fabricator.support.User

description "blueprints can have a custom constructor closure will use that to instantiate the objects"

scenario "blueprint with an constructor", {
	given "a blueprint is defined with an constructor", {
		User.blueprint {
			constructor { new User(email: "email.from.constructor@example.com") }
			
			first "John"
			last "Doe"
		}
	}
	
	when "an object is buildd", {
		user = User.build()
	}
	
	then "it should be created through the constructor", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "email.from.constructor@example.com"
	}
}

scenario "default constructor", {
	given "a default constructor is defined", {
		invoked = ["value": false]
		
		Fabricator.define() {
			constructor { klass ->
				invoked.value = true
				klass.newInstance()
			}
		}
		
		User.blueprint {
			first "Jane"
			last "Doe"
			email "jane.doe@example.com"
		}
	}
	
	when "an object is built", {
		jane = User.build()
	}
	
	then "it should be created by invoking the default constructor", {
		invoked.value.shouldBe true
	}
	
	then "it should be populated with the correct values", {
		jane.first.shouldBe "Jane"
		jane.last.shouldBe "Doe"
		jane.email.shouldBe "jane.doe@example.com"
	}
}

scenario "blueprint with custom constructor, using a transient property as a constructor parameter", {
	given "a custom constructor is defined", {
		Company.blueprint {
			constructor { new Company(name) }
			
			ignore {
				name "name"
			}
			
			ticker "ticker"
		}
	}
	
	when "an object is buildd", {
		apple = Company.build(name: "Apple Inc.", ticker: "AAPL")
	}
	
	then "it should be populated with the correct values", {
		apple.name.shouldBe "Apple Inc."
		apple.ticker.shouldBe "AAPL"
	}
}