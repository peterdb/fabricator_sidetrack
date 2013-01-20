package fabricator

import fabricator.support.Named;

public class Sequence {

	final String name
	final List aliases
	final Closure closure
	
	Object current
	
	public Sequence(String name, Map options = [:], Closure closure = null) {
		this.name = name
		this.aliases = options["aliases"]
		current = options["start"]	
		this.closure = closure
	}
	
	public Object next() {
		return closure ? closure(current++) : current++
	}
}
