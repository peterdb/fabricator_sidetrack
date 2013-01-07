package be.pinpoint.fabricator.properties

import be.pinpoint.fabricator.Property

/**
 * dynamic property 
 * 
 * @author peter
 */
class Dynamic extends Property {

	final Closure closure
	
	public Dynamic(String name, Closure closure) {
		super(name)
		
		this.closure = closure
	}
	
	@Override
	public Closure toClosure() {
		return closure
	}

}
