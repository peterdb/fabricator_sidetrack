package fabricator

import fabricator.declaration.Declaration;
import fabricator.declaration.DeclarationList;

class Definition {
	final DeclarationList declarations = new DeclarationList()
	Closure constructor
	
	public void declareProperty(Declaration declaration) {
		declarations << declaration
	}
	
	public List getProperties() {
		return declarations.toProperties()
	}
}
