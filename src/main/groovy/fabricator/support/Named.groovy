package fabricator.support

class Named {
	String name
	List aliases
	
	public String[] names() {
		return this.aliases + this.name
	}
}
