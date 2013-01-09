package fabricator.properties

import fabricator.Property

/**
 * dynamic property 
 * 
 * @author peter
 */
class Dynamic extends Property {

	final Closure closure
	
	public Dynamic(String name, boolean ignored, Closure closure) {
		super(name, ignored)
		
		this.closure = closure
	}
	
	@Override
	public Closure toClosure() {
		return closure
	}

}
