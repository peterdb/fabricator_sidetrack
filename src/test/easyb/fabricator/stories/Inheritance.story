package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.User

scenario "define a factory", {
	given "a factory is defined", {
		Fabricator.define {
			factory(User) {
				first "John"
				last "Doe"
				email { "$first.$last@example.com" }
				
				factory("admin") {
					admin true
				}
			}
		}
	}

	then "it registers a factory with the name", {
		Fabricator.configuration.with {
			factoryByName("user").shouldNotBe null
			factoryByName("admin").shouldNotBe null
			factoryByName("admin").parent.shouldBe factoryByName("user")
		}
	}
}