package fabricator

import fabricator.support.Named;


public abstract class Property {
	final String name
	final boolean ignored
	
	public Property(String name, boolean ignored) {
		assert name, "name cannot be empty or null"
		
		this.name = name
		this.ignored = ignored
	}
	
	/**
	 * Create a {@link Closure} returning the value for the property.
	 * @return the {@link Closure}
	 */
	public abstract Closure toClosure();
}
