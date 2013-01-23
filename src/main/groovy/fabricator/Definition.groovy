package fabricator

import fabricator.declaration.Declaration;
import fabricator.declaration.DeclarationList;

class Definition {
	final String name
	final DeclarationList declarations = new DeclarationList()
	final List declaredTraits = []
	final List traits = []
	Closure instantiator
	
	public Definition(String name = null, List traits = []) {
		this.name = name
		this.traits = traits
	}
	
	public void declareProperty(Declaration declaration) {
		declarations << declaration
	}
	
	public void defineTrait(Trait trait) {
		declaredTraits << trait
	}
	
	public void inheritTraits(List traits) {
		this.traits.addAll(traits)
	}
	
	public Trait traitByName(String name) {
		return declaredTraits.find { it.name == name }
	}
	
	public List getProperties() {
		def properties = []
		
		properties.addAll(declarations.toProperties())
		traits.each { trait -> properties.addAll(trait.properties) } 
		
		println "props: from definition $name"+ properties
		
		return properties
	}
}
