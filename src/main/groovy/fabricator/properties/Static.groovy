package fabricator.properties

import fabricator.Property
import groovy.transform.ToString;

/**
 * static property 
 * 
 * @author peter
 */
@ToString(includeNames=true, includeSuper=true)
class Static extends Property {

	final Object value
	
	public Static(String name, boolean ignore, Object value) {
		super(name, ignore)
		
		this.value = value
	}
	
	@Override
	public Closure toClosure() {
		return { value }
	}

}
