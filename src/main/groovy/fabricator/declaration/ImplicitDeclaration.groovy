package fabricator.declaration

import fabricator.Fabricator
import fabricator.Blueprint;
import fabricator.Property
import fabricator.properties.Association
import fabricator.properties.SequenceProperty
import fabricator.properties.Static

/**
 * Static property definition
 */
class ImplicitDeclaration extends Declaration {

	final Blueprint factory
	
	public ImplicitDeclaration(String name, Blueprint factory = null, boolean ignore) {
		super(name, ignore)
		
		this.factory = factory
	}

	@Override
	public Property toProperty() {
		if(Fabricator.configuration.blueprints.containsKey(name)) {
			return new Association(name, name)
		} else if(Fabricator.configuration.sequences.containsKey(name)) {
			return new SequenceProperty(name, ignore, name)
		} else {
			throw new IllegalArgumentException("unsupported")
		}
	}
}
