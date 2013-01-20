package fabricator

import groovy.lang.Delegate;

class Trait {

	@Delegate
	final Definition definition

	final String name
	
}
