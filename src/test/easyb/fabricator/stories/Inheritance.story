package fabricator.stories

import fabricator.Fabricator;
import fabricator.support.User

description ""

scenario "definition of a blueprint inside another blueprint", {
	given "an empty configuration", {
		Fabricator.reset()
	}
	
	when "a blueprint is defined inside another blueprint", {
		user = User.blueprint {
			blueprint("admin")
		}
		
		admin = Fabricator.blueprintByName("admin")
	}

	then "two blueprints are created, the outer blueprint is the parent of the inner blueprint", {
		user.parent.shouldBe null
		admin.parent.shouldBe user
	}
}

scenario "definition of a blueprint with explicit parent", {
	given "an empty configuration", {
		Fabricator.reset()
	}
	
	when "a blueprint is defined with an explicit parent", {
		user = User.blueprint()
		admin = User.blueprint("admin", parent: "user")
	}

	then "two blueprints are created, the user blueprint is set as parent", {
		user.parent.shouldBe null
		admin.parent.shouldBe user
	}
}