package fabricator

import fabricator.support.Named;

public class Sequence extends Named {

	final Closure closure

	Object currentValue
	
	public Sequence(String name, List aliases, Object value = 0, Closure closure = null) {
		super(name, aliases)
		
		assert value != null, "value cannot be null"
		assert value.respondsTo("next"), "values doesn't respond to next"
		
		currentValue = value
		this.closure = closure
	}
	
	public Object next() {
		return closure ? closure(currentValue++) : currentValue++
	}
}
