package fabricator.declaration

import fabricator.Fabricator
import fabricator.Factory;
import fabricator.Property
import fabricator.properties.Association
import fabricator.properties.SequenceProperty
import fabricator.properties.Static

/**
 * Static property definition
 */
class ImplicitDeclaration extends Declaration {

	final Factory factory
	
	public ImplicitDeclaration(String name, Factory factory = null, boolean ignore) {
		super(name, ignore)
		
		this.factory = factory
	}

	@Override
	public Property toProperty() {
		if(Fabricator.configuration.factories.containsKey(name)) {
			return new Association(name, name)
		} else if(Fabricator.configuration.sequences.containsKey(name)) {
			return new SequenceProperty(name, ignore, name)
		} else {
			factory.inheritTraits([name])
			return null
		}
	}
}
