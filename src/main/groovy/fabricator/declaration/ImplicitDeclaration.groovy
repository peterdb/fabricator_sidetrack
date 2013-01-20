package fabricator.declaration

import fabricator.Fabricator
import fabricator.Property
import fabricator.properties.Association
import fabricator.properties.SequenceProperty
import fabricator.properties.Static

/**
 * Static property definition
 */
class ImplicitDeclaration extends Declaration {

	public ImplicitDeclaration(String name, boolean ignore) {
		super(name, ignore)
	}

	@Override
	public Property toProperty() {
		if(Fabricator.configuration.factories.containsKey(name)) {
			return new Association(name, name)
		}
		if(Fabricator.configuration.sequences.containsKey(name)) {
			return new SequenceProperty(name, ignore, name)
		}

		throw new IllegalArgumentException("no factory or sequence found for name $name")
	}
}
