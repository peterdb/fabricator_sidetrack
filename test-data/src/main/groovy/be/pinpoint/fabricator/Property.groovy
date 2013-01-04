package be.pinpoint.fabricator

public abstract class Property {
	final String name
	
	public Property(String name) {
		assert name, "name cannot be empty or null"
		
		this.name = name
	}
	
	public abstract Closure toClosure();
}
