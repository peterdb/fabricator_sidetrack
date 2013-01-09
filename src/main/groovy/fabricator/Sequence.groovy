package fabricator

import fabricator.support.Named;

public class Sequence extends Named {

	final Closure closure
	final Object start
	
	Object current
	
	public Sequence(String name, List aliases, Object value = 0, Closure closure = null) {
		super(name, aliases)
		
		assert value != null, "value cannot be null"
		assert value.respondsTo("next"), "values doesn't respond to next"
	
		start = value	
		this.closure = closure
	}
	
	public Object next() {
		if(current == null) {
			current = start
		}
		
		return closure ? closure(current++) : current++
	}
	
	/**
	 * reset this sequence
	 */
	public void reset() {
		current = null
	}
}
