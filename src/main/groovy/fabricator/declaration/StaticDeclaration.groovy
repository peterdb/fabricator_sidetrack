package fabricator.declaration

import fabricator.Property;
import fabricator.properties.Static;

/**
 * Static property declaration
 */
class StaticDeclaration extends Declaration<Static> {

	final Object value

	public StaticDeclaration(String name, Object value, boolean ignore) {
		super(name, ignore)

		this.value = value
	}

	@Override
	public Static toProperty() {
		return new Static(name, ignore, value);
	}
}
