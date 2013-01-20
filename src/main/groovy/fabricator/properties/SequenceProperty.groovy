package fabricator.properties

import fabricator.Fabricator;
import fabricator.Property
import fabricator.Sequence
import groovy.transform.ToString;

/**
 * sequence property 
 * 
 * @author peter
 */
@ToString(includeSuper=true, includeNames=true)
class SequenceProperty extends Property {

	final String sequence

	public SequenceProperty(String name, boolean ignore, String sequence) {
		super(name, ignore)

		this.sequence = sequence
	}

	@Override
	public Closure toClosure() {
		return { sequenceName, args -> Fabricator.generate(sequenceName) }.curry(this.sequence)
	}
}
