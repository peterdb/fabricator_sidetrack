package be.pinpoint.fabricator.properties

import be.pinpoint.fabricator.Property
import be.pinpoint.fabricator.Factory

/**
 * association property 
 * 
 * @author peter
 */
class Association extends Property {

	final Factory factory
	final Integer count
	final Closure closure

	public Association(String name, Factory factory, Integer count = 1) {
		super(name)

		this.factory = factory
		this.count = count
	}
	
	public Association(String name, Factory factory, Integer count = 1, Closure closure) {
		super(name)

		this.factory = factory
		this.count = count
		this.closure = closure
	}

	@Override
	public Closure toClosure() {
		Closure creator = closure ? closure : { factory.run() }
		
		return {
			if(count > 1) {
				def results = []
				count.times {
					results.add(creator.call())
				}
				return results
			} else {
				creator.call()
			}
		}
	}
}
