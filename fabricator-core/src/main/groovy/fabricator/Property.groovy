package fabricator

import groovy.lang.Closure;

public abstract class Property {
	final String name
	
	public Property(String name) {
		assert name, "name cannot be empty or null"
		
		this.name = name
	}
	
	/**
	 * Create a {@link Closure} returning the value for the property.
	 * @return the {@link Closure}
	 */
	public abstract Closure toClosure();
}
