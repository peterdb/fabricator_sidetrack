package fabricator.properties

import fabricator.Property
import fabricator.Sequence

/**
 * sequence property 
 * 
 * @author peter
 */
class SequenceProperty extends Property {

	final Sequence sequence

	public SequenceProperty(String name, boolean ignore, Sequence sequence) {
		super(name, ignore)

		this.sequence = sequence
	}

	@Override
	public Closure toClosure() {
		def tmp = this.sequence
		
		return { println "jup"; tmp++ }
	}
}
