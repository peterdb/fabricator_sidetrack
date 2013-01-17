package fabricator

import fabricator.support.Named;

public class Sequence extends Named {

	final Closure closure
	
	Object current
	
	public Sequence(String name, List aliases, Object value = 0, Closure closure = null) {
		super(name, aliases)
		
		assert value != null, "value cannot be null"
		assert value.respondsTo("next"), "values doesn't respond to next"
	
		current = value	
		this.closure = closure
	}
	
	public Object next() {
		return closure ? closure(current++) : current++
	}
}
