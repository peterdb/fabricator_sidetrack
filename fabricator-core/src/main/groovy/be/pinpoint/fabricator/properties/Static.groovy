package be.pinpoint.fabricator.properties

import be.pinpoint.fabricator.Property

/**
 * static property 
 * 
 * @author peter
 */
class Static extends Property {

	final Object value
	
	public Static(String name, Object value) {
		super(name)
		
		this.value = value
	}
	
	@Override
	public Closure toClosure() {
		return { value }
	}

}
