package fabricator.stories

import fabricator.Fabricator
import fabricator.support.Company
import fabricator.support.User

description "factories can be passed an instantiator closure will use that to instantiate the objects"

scenario "factory with an instantiator", {
	given "a factory is defined with an instantiator", {
		Fabricator.define() {
			factory(User) {
				instantiator { new User(email: "email.from.instantiator@example.com") }
				
				first "John"
				last "Doe"
			}
		}
	}
	
	when "an object is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should be created through the instantiator", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "email.from.instantiator@example.com"
	}
}

scenario "default instantiator", {
	given "a default instantiator is defined", {
		invoked = ["value": false]
		
		Fabricator.define() {
			instantiator { klass ->
				invoked.value = true
				klass.newInstance()
			}
			
			factory(User) {
				first "Jane"
				last "Doe"
				email "jane.doe@example.com"
			}
		}
	}
	
	when "an object is fabricated", {
		jane = Fabricator.fabricate(User)
	}
	
	then "it should be created by invoking the default instantiator", {
		invoked.value.shouldBe true
	}
	
	then "it should be populated with the correct values", {
		jane.first.shouldBe "Jane"
		jane.last.shouldBe "Doe"
		jane.email.shouldBe "jane.doe@example.com"
	}
}

scenario "factory with custom instantiator, using a transient property as a constructor parameter", {
	given "a custom instantiator is defined", {
		Fabricator.define() {
			factory(Company) {
				instantiator { new Company(name) }
				
				ignore {
					name "name"
				}
				
				ticker "ticker"
			}
		}
	}
	
	when "an object is fabricated", {
		apple = Fabricator.fabricate(Company, name: "Apple Inc.", ticker: "AAPL")
	}
	
	then "it should be populated with the correct values", {
		apple.name.shouldBe "Apple Inc."
		apple.ticker.shouldBe "AAPL"
	}
}