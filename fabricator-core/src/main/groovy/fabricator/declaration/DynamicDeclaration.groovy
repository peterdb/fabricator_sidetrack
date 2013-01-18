package fabricator.declaration

import fabricator.Property
import fabricator.properties.Dynamic

/**
 * Static property definition
 */
class DynamicDeclaration extends Declaration {

	final Closure closure
	
	public DynamicDeclaration(String name, boolean ignore, Closure closure) {
		super(name, ignore)
		
		this.closure = closure
	}

	@Override
	public Property toProperty() {
		return new Dynamic(name, ignore, closure)
	}
}
