package fabricator.properties

import fabricator.Property
import fabricator.Sequence

/**
 * Sequence property 
 * 
 * @author peter
 */
class SequenceProperty extends Property {

	final Sequence sequence

	public SequenceProperty(String name, Sequence sequence) {
		super(name)

		this.sequence = sequence
	}

	@Override
	public Closure toClosure() {
		return {
			sequence.next()
		}
	}
}
