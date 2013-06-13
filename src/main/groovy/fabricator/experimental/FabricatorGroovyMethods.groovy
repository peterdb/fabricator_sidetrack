package fabricator.experimental

import fabricator.Fabricator

/**
 * Enable <code>Class.fabricate</code> methods to create objects.
 * 
 * @author peter
 */
class FabricatorGroovyMethods {

	// TODO investigate groovy 2 extension modules: http://docs.codehaus.org/display/GROOVY/Creating+an+extension+module
	
	public static <T> T fabricate(Class<T> klass, Object[] args) {
		def overrides = args.find { it instanceof Map }
		def factory = args.find { it instanceof String }

		Fabricator.fabricate(overrides, factory ? factory : klass)
	}
	
	/**
	 * Installs the <code>fabricate</code> method in <code>Class.metaClass</code>
	 */
	public static void install() {
		Class.metaClass.fabricate = { Object[] args ->
			FabricatorGroovyMethods.fabricate(delegate, args)
		}
	}
}
