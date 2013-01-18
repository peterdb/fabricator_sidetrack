package fabricator.properties

import fabricator.Fabricator;
import fabricator.Property
import fabricator.Factory

/**
 * association property 
 * 
 * @author peter
 */
class Association extends Property {

	final String factory
	final Map overrides

	public Association(String name, String factory, Map overrides = [:]) {
		super(name, false)

		this.factory = factory
		this.overrides = overrides
	}
	
	@Override
	public Closure toClosure() {
		return { Fabricator.fabricate(overrides, factory) }
	}
}
