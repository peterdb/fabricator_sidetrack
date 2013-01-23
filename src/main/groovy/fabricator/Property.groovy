package fabricator

import groovy.transform.ToString;

@ToString(includeNames=true)
public abstract class Property {
	final String name
	final boolean ignore
	
	public Property(String name, boolean ignore) {
		assert name, "name cannot be empty or null"
		
		this.name = name
		this.ignore = ignore
	}
	
	/**
	 * Create a {@link Closure} returning the value for the property.
	 * @return the {@link Closure}
	 */
	public abstract Closure toClosure();
}
