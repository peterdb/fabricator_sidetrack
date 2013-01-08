package fabricator

import fabricator.support.CustomIncrementable

description "sequence specifications"

scenario "with no arguments", {
	given "a sequence with no arguments"
	then "it creates a default sequence"
}

scenario "with only a name argument", {
	given "a sequence with a name", { Fabricator.sequence("seq") }

	then "it starts with 0", {
		Fabricator.generate("seq").shouldBe 0
	}

	then "it increments by one with each call", {
		Fabricator.generate("seq").shouldBe 1
		Fabricator.generate("seq").shouldBe 2
		Fabricator.generate("seq").shouldBe 3
		Fabricator.generate("seq").shouldBe 4
	}
}

scenario "with a name and starting number", {
	given "a sequence with a name and starting number", {
		Fabricator.sequence("higher", 69)
	}

	then "it starts with the number provided", {
		Fabricator.generate("higher").shouldBe 69
	}

	then "it increments by one with each call", {
		Fabricator.generate("higher").shouldBe 70
		Fabricator.generate("higher").shouldBe 71
		Fabricator.generate("higher").shouldBe 72
		Fabricator.generate("higher").shouldBe 73
	}
}

scenario "two sequences increment separately", {
	given "two sequences", {
		Fabricator.sequence("incr1")
		Fabricator.sequence("incr2")
	}

	then "they start with 0", {
		Fabricator.generate("incr1").shouldBe 0
		Fabricator.generate("incr2").shouldBe 0
	}

	then "they increment separately by one with each call", {
		Fabricator.generate("incr1").shouldBe 1
		Fabricator.generate("incr2").shouldBe 1
		Fabricator.generate("incr1").shouldBe 2
		Fabricator.generate("incr2").shouldBe 2
		Fabricator.generate("incr1").shouldBe 3
		Fabricator.generate("incr2").shouldBe 3
		Fabricator.generate("incr1").shouldBe 4
		Fabricator.generate("incr2").shouldBe 4
	}
}

scenario "with a block", {
	given "a sequence with a closure", {
		Fabricator.sequence("email", { n -> "user$n@example.com" })
	}
	
	then "it passes the number to the closure and returns the value", {
		Fabricator.generate("email").shouldBe "user0@example.com"
	}

	then "it increments by one with each call", {
		Fabricator.generate("email").shouldBe "user1@example.com"
		Fabricator.generate("email").shouldBe "user2@example.com"
	}
}

scenario "with a char value", {
	given "a sequence with a char starting value", {
		Fabricator.sequence("char", 'd')
	}
	
	then "it starts with the char provided", {
		Fabricator.generate("char").shouldBe 'd'
	}

	then "it increments by one with each call", {
		Fabricator.generate("char").shouldBe 'e'
		Fabricator.generate("char").shouldBe 'f'
		Fabricator.generate("char").shouldBe 'g'
		Fabricator.generate("char").shouldBe 'h'
	}
}

scenario "with a custom incrementable capable value", {
	given "a sequence with a custom incrementable cabable starting value", {
		Fabricator.sequence("custom", new CustomIncrementable(99))
	}
	
	then "it starts with the char provided", {
		Fabricator.generate("custom").value.shouldBe 99
	}

	then "it increments by one with each call", {
		Fabricator.generate("custom").value.shouldBe 100
		Fabricator.generate("custom").value.shouldBe 101
		Fabricator.generate("custom").value.shouldBe 102
		Fabricator.generate("custom").value.shouldBe 103
	}
}

scenario "sequence reset", {
	given "a sequence"
	when "it is reset"
	then "it should restart at 0"
}

scenario "sequence reset with a starting number", {
	given "a sequence with a starting number"
	when "it is reset"
	then "it should restart at the starting number"
}

scenario "with a default sequence start", {
	given "a default sequence start value has been configured"
	then "it starts a new sequence at the default"
	then "it respects start value passed as an argument"
}