package be.pinpoint.fabricator

public class Sequence {

	final String name
	final Closure closure

	Object currentValue
	
	public Sequence(String name, Object value = 0, Closure closure = null) {
		assert name, "name cannot be null or empty"
		assert value != null, "value cannot be null"
		assert value.respondsTo("next"), "values doesn't respond to next"
		
		this.name = name
		currentValue = value
		this.closure = closure
	}
	
	public Object next() {
		return closure ? closure(currentValue++) : currentValue++
	}
}
