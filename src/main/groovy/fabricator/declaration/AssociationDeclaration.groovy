package fabricator.declaration

import fabricator.Property
import fabricator.properties.Association

/**
 * Static property definition
 */
class AssociationDeclaration extends Declaration {

	final List options
	final Map overrides

	public AssociationDeclaration(String name, options) {
		super(name, false)

		this.options = options.clone()
		this.overrides = options.find { it instanceof Map }
	}

	@Override
	public Property toProperty() {
		String factoryName = overrides["factory"] || name
		overrides.remove("factory")

		return new Association(name, factoryName, [options, overrides].flatten())
	}
}
