package fabricator.declaration

import fabricator.Property


/**
 * Property declaration, also acts as a factory for {@link Property} instances.
 * 
 * @author peter
 */
abstract class Declaration<T extends Property> {

	final name
	final boolean ignore
	
	Declaration(def name, ignore) {
		this.name = name
		this.ignore = ignore
	}
	
	abstract T toProperty();
	
}
