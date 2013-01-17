package fabricator.properties

import fabricator.Property

/**
 * dynamic property 
 * 
 * @author peter
 */
class Dynamic extends Property {

	final Closure closure
	
	public Dynamic(String name, boolean ignore, Closure closure) {
		super(name, ignore)
		
		this.closure = closure
	}
	
	@Override
	public Closure toClosure() {
		return closure
	}

}
