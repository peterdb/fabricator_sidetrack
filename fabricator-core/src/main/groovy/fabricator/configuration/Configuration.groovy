package fabricator.configuration

import fabricator.DefinitionProxy
import fabricator.Factory
import fabricator.Sequence
import fabricator.support.DefaultNamingStrategy
import fabricator.support.NamedRegistry
import fabricator.support.NamingStrategy

class Configuration {
	final Map<String, Sequence> sequences = [:]
	final Map<String, Factory> factories = [:]

	Closure instantiator = { classToCreate -> classToCreate.newInstance() }
	NamingStrategy naming = new DefaultNamingStrategy()
	final afterCreateCallbacks = []

	public void defaultInstantiator(Closure closure) {
		instantiator = closure
	}
	
	public void registerSequence(Sequence sequence) {
		assert sequence, "sequence cannot be null"

		sequences.put(sequence.name, sequence)
		sequences.aliases.each { alias ->
			sequences.put(alias, sequence)
		}
	}

	public Sequence sequenceByName(name) {
		assert name, "name cannot be null or empty"

		return sequences[name]
	}

	public void registerFactory(Factory factory) {
		assert factory, "factory cannot be null"

		factories.put(factory.name, factory)
		factory.aliases.each { alias ->
			factories.put(alias, factory)
		}
	}

	public Factory factoryByName(String name) {
		return factories[name]
	}

	public Factory factoryByClass(Class klass) {
		def name = naming.nameFor(klass)

		return factoryByName(name)
	}

	public void afterCreate(Closure closure) {
		afterCreateCallbacks << closure
	}
}
