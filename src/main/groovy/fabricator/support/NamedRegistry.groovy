package fabricator.support

/**
 * A registry for {@link Named} instances.
 * 
 * @author peter
 */
class NamedRegistry<T extends Named> {

	final Map<String, T> values = new LinkedHashMap<String, T>()

	void register(T named) {
		assert named != null, "named cannot be null"
		
		named.names().each { name ->
			values[name] = named
		}
	}
	
	boolean isRegistered(String name) {
		return values.containsKey(name)
	}

	// enable registry[name] syntax
	T getAt(String name) {
		assert name!= null, "named cannot be null"
		
		return values[name]
	}
}
