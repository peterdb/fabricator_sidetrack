package fabricator

import fabricator.declaration.Declaration;
import fabricator.declaration.DeclarationList;

class Definition {
	final String name
	final DeclarationList declarations = new DeclarationList()
	final List declaredTraits = []
	Closure instantiator
	
	public Definition(String name = null) {
		this.name = name
	}
	
	public void declareProperty(Declaration declaration) {
		declarations << declaration
	}
	
	public List getProperties() {
		return declarations.toProperties()
	}
}
