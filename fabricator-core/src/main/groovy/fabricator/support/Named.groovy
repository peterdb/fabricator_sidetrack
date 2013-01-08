package fabricator.support

class Named {
	final String name
	final List aliases
	
	public Named(String name, List aliases = []) {
		assert name, "name cannot be empty or null"
		
		this.name = name
		this.aliases = aliases
	}
	
	public String[] names() {
		return this.aliases + this.name
	}
}
