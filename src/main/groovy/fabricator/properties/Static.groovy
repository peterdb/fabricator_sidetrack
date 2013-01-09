package fabricator.properties

import fabricator.Property
import groovy.transform.ToString;

/**
 * static property 
 * 
 * @author peter
 */
@ToString
class Static extends Property {

	final Object value
	
	public Static(String name, boolean ignored, Object value) {
		super(name, ignored)
		
		this.value = value
	}
	
	@Override
	public Closure toClosure() {
		return { value }
	}

}
