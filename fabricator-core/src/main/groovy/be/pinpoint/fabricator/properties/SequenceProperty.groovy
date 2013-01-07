package be.pinpoint.fabricator.properties

import be.pinpoint.fabricator.Property
import be.pinpoint.fabricator.Sequence

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
