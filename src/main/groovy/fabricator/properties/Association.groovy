package fabricator.properties

import fabricator.Fabricator;
import fabricator.Property
import fabricator.Blueprint

/**
 * association property 
 * 
 * @author peter
 */
class Association extends Property {

	final String blueprint
	final Map overrides

	public Association(String name, String blueprint, Map overrides = [:]) {
		super(name, false)

		this.blueprint = blueprint
		this.overrides = overrides
	}
	
	@Override
	public Closure toClosure() {
		return { Fabricator.build(overrides, blueprint) }
	}
}
