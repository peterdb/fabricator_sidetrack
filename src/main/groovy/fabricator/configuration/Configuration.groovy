package fabricator.configuration

import fabricator.Definition
import fabricator.Blueprint
import fabricator.Sequence

class Configuration {
	Map<String, Sequence> sequences = [:]
	Map<String, Blueprint> blueprints = [:]
	
	@Delegate
	final Definition definition = new Definition()

	String blueprintsPath = "src/test/blueprints"
	
	public Configuration() {
		definition.constructor = { Class klass -> klass.newInstance() }
	}
		
	public void registerSequence(Sequence sequence) {
		assert sequence, "sequence cannot be null"

		sequences.put(sequence.name, sequence)
		sequence.aliases.each { alias ->
			sequences.put(alias, sequence)
		}
	}

	public Sequence sequenceByName(name) {
		assert name, "name cannot be null or empty"

		return sequences[name]
	}

	public void registerBlueprint(Blueprint blueprint) {
		assert blueprint, "blueprint cannot be null"

		blueprint.names.each { name ->
			blueprints[name] = blueprint
		}
	}

	public Blueprint blueprintByName(String name) {
		return blueprints[name]
	}
}
