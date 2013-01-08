package fabricator.properties

import fabricator.Property
import fabricator.Factory

/**
 * association property 
 * 
 * @author peter
 */
class Association extends Property {

	final Factory factory
	final Integer count
	final Closure closure
	final Map overrides

	public Association(String name, Factory factory, Integer count, Map overrides = [:]) {
		super(name)

		assert factory, "factory cannot be null"
		assert overrides != null, "overrides cannot be null"
		assert count, "count must be positive non-null integer"

		this.factory = factory
		this.count = count
		this.overrides = overrides
	}
	
	public Association(String name, Factory factory, Integer count, Map overrides = [:], Closure closure) {
		super(name)

		this.factory = factory
		this.count = count
		this.closure = closure
	}

	@Override
	public Closure toClosure() {
		Closure creator = closure ? closure : { factory.run(overrides) }
		
		return {
			if(count > 1) {
				def results = []
				count.times { n ->
					results.add(creator.call(n))
				}
				return results
			} else {
				creator.call()
			}
		}
	}
}
