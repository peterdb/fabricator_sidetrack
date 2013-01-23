package fabricator.stories

import fabricator.Blueprint;
import fabricator.Fabricator;
import fabricator.support.Kitten
import fabricator.support.Post
import fabricator.support.User

description "Blueprint names and aliases"

scenario "a default blueprint should have the lowercase simple classname as name", {
	when "a blueprint is created without a name", {
		blueprint = User.blueprint()
	}
	
	then "it should have the lowercase classname as name", {
		blueprint.name.shouldBe "user"
	}
	
	and "it should have no aliases", {
		blueprint.aliases.shouldBe []
	}
	
	and "names should contain only the name", {
		blueprint.names.shouldBe (["user"]) // why does this need parenthesis?
	}
}

scenario "a blueprint can have a name", {
	when "a blueprint is created with a name", {
		blueprint = User.blueprint("admin")
	}
	
	then "it should have the given name as name", {
		blueprint.name.shouldBe "admin"
	}
	
	and "it should have no aliases", {
		blueprint.aliases.shouldBe []
	}
	
	and "names should contain only the name", {
		blueprint.names.shouldBe (["admin"])
	}
}

scenario "a blueprint with aliases", {
	when "a blueprint is created with a name and aliases", {
		blueprint = User.blueprint("user", aliases: ["author", "admin"])
	}
	
	then "it should have the given name", {
		blueprint.name.shouldBe "user"
	}

	and "it should have the given aliases", {
		blueprint.aliases.shouldBe (["author", "admin"])
	}
	
	and "names should contain name and aliases", {
		blueprint.names.shouldBe (["user", "author", "admin"])
	}
}