package fabricator.support

import fabricator.SequenceSpecs;

/**
 * Object that can be incremented. Used in {@link SequenceSpecs}
 * 
 * @author peterdb
 */
class CustomIncrementable {

	final int value
	
	public CustomIncrementable(int value) {
		this.value = value
	}
	
	CustomIncrementable next() {
		new CustomIncrementable(value + 1)
	}
	
}
