package fabricator.declaration

import fabricator.Property
import fabricator.properties.Association

/**
 * Static property definition
 */
class AssociationDeclaration extends Declaration {

	final Map overrides
	final String blueprint
	
	public AssociationDeclaration(String name, Map options) {
		super(name, false)

		def overrides = options.clone()
		
		this.blueprint = overrides["blueprint"] ?: name 
		overrides.remove("blueprint")
		
		this.overrides = overrides
	}

	@Override
	public Property toProperty() {
		return new Association(this.name, blueprint, overrides)
	}
}
