package fabricator.declaration

import fabricator.Property

abstract class Declaration {

	final name
	final boolean ignore
	
	Declaration(def name, ignore) {
		this.name = name
		this.ignore = ignore
	}
	
	abstract Property toProperty();
	
}
