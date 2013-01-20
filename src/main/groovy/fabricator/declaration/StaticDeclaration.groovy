package fabricator.declaration

import fabricator.Property;
import fabricator.properties.Static;

/**
 * Static property definition
 */
class StaticDeclaration extends Declaration {

	final Object value
	
	public StaticDeclaration(String name, Object value, boolean ignore) {
		super(name, ignore)
		
		this.value = value
	}
	
	@Override
	public Property toProperty() {
		return new Static(name, ignore, value);
	}

}
