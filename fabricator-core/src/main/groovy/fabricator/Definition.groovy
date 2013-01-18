package fabricator

import fabricator.declaration.Declaration;
import fabricator.declaration.DeclarationList;

class Definition {
	final String name
	final DeclarationList declarations = new DeclarationList()
	
	public Definition(String name) {
		this.name = name
	}
	
	public void declareProperty(Declaration declaration) {
		declarations.add(declaration)
	}
}
