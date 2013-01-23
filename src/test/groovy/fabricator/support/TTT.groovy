package fabricator.support

import fabricator.Fabricator

Kitten.blueprint {
	name "generic kitten"
	
	blueprint("garfield") {
		name "Garfield"
	}
}

User.blueprint {
	first "John"
	last "Doe"
	kitten(blueprint: "garfield") 
}

println User.build()