package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.Kitten;
import fabricator.support.User

description "build stories"

scenario "build without params", {
	given "a simple blueprint", {
		User.blueprint {
			first "John"
			last "Doe"
			email { "$first.$last@example.com" }
		}
	}
	
	when "a user is built", {
		user = User.build()
	}
	
	then "it should have a kitten", {
		user.first.shouldBe "John"
		user.last.shouldBe "Doe"
		user.email.shouldBe "John.Doe@example.com"
	}
}

scenario "build with blueprint name", {
	given "some blueprints", {
		User.blueprint {
			first "John"
			last "Doe"
			email { "$first.$last@example.com" }
			
			blueprint("admin") {
				admin true
			}
		}
	}
	
	when "build is called with a blueprint name", {
		user = User.build("admin")
	}
	
	then "it should use the correct blueprint", {
		user.admin.shouldBe true
	}
}

scenario "build with overrides", {
	given "some blueprints", {
		User.blueprint {
			first "John"
			last "Doe"
			email { "$first.$last@example.com" }
		}
	}
	
	when "build is called with overrides", {
		user = User.build(first: "Jane", admin: true)
	}
	
	then "it should use the correct blueprint", {
		user.first.shouldBe "Jane"
		user.last.shouldBe "Doe"
		user.admin.shouldBe true
	}
}

