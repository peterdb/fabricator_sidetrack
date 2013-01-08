package fabricator

import fabricator.Fabricator;
import fabricator.support.User

scenario "simple numeric sequence", {
	given "a simple numeric sequence", {
		sequence = Fabricator.sequence("simple")
	}

	then "it starts with 0", { 
		sequence.next().shouldBe 0
	}

	and "it should increment", { 
		sequence.next().shouldBe 1
		sequence.next().shouldBe 2
		sequence.next().shouldBe 3
	}
}

scenario "numeric sequence with a starting value", {
	given "a simple numeric sequence with a starting value", {
		sequence = Fabricator.sequence("simple", 10)
	}

	then "it starts with 10", {
		sequence.next().shouldBe 10
	}


	and "it should increment", {
		sequence.next().shouldBe 11
		sequence.next().shouldBe 12
		sequence.next().shouldBe 13
	}
}

scenario "string sequence", {
	given "a string sequence starting with a", {
		sequence = Fabricator.sequence("string", "d")
	}

	then "it starts with d", {
		sequence.next().shouldBe "d"
	}


	and "it should increment", {
		sequence.next().shouldBe "e"
		sequence.next().shouldBe "f"
		sequence.next().shouldBe "g"
	}
}

scenario "sequence with closure", {
	given "a numeric sequence with a closure", {
		sequence = Fabricator.sequence("email", 10, { n -> "person$n@gmail.com" })
	}

	then "it uses the closure to create the result", {
		sequence.next().shouldBe "person10@gmail.com"
		sequence.next().shouldBe "person11@gmail.com"
		sequence.next().shouldBe "person12@gmail.com"
		sequence.next().shouldBe "person13@gmail.com"
	}
}