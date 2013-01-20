package fabricator.support

/**
 * default: lowercase simple name
 *
 * @author peter
 */
class DefaultNamingStrategy implements NamingStrategy {

	@Override
	public String nameFor(Class klass) {
		assert klass != null
		
		return klass.getSimpleName().toLowerCase()
	}

}
