package fabricator

import fabricator.declaration.Declaration;
import fabricator.declaration.DeclarationList;

class Definition {
	final String name
	final DeclarationList declarations = new DeclarationList()
	final List traits = []
	Closure instantiator
	
	public Definition(String name) {
		this.name = name
	}
	
	public void declareProperty(Declaration declaration) {
		declarations << declaration
	}
	
	public void defineTrait(Trait trait) {
		traits << trait
	}
}
