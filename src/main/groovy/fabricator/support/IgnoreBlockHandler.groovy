package fabricator.support

import fabricator.Factory;

/**
 * Delegate property definitions to {@link Factory#propertyMissing(String)} with 
 * an extra property: <code>ignore</ignore> with value <code>true</code>
 * 
 * @author peter
 */
class IgnoreBlockHandler {
	final Factory factory

	public IgnoreBlockHandler(Factory factory) {
		this.factory = factory
	}

	def propertyMissing(String name) {
		def args = [{ignore: true}]
		
		return factory.methodMissing(name, args)
	}

	def methodMissing(String name, args) {
		// check if first arg is a map
		def newArgs = new ArrayList(Arrays.asList(args))
		newArgs.add(0, [ignore: true])
		
		return factory.methodMissing(name, newArgs)
	}
}
