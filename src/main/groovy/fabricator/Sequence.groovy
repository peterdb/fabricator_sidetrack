package fabricator

import fabricator.support.Named;
import groovy.transform.ToString;

@Mixin(Named)
public class Sequence {

	final Closure generator
	
	Object current
	
	public Sequence(String name, Map options = [:], Closure generator = null) {
		this.name = name
		this.aliases = options["aliases"]
		this.generator = generator
		
		current = options["start"] ?: 0
	}
	
	public Object next() {
		return generator ? generator(current++) : current++
	}
}
