package fabricator

import groovy.lang.Delegate;

class Trait {

	@Delegate
	final Definition definition

	final String name
	
	public Trait(String name) {
		this.name = name
		
		definition = new Definition(name)
	}
	
}
