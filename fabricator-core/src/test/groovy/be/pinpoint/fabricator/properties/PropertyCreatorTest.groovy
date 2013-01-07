package be.pinpoint.fabricator.properties;

import org.junit.Before
import org.junit.Test

import be.pinpoint.fabricator.Fabricator
import be.pinpoint.fabricator.support.Kitten
import be.pinpoint.fabricator.support.Post;

class PropertyCreatorTest {

	private PropertyCreator creator

	@Before
	public void setupFabrication() {
		Fabricator.define(Kitten)
		Fabricator.define("posts", Post)
		Fabricator.sequence("sequence")
	}
		
	@Before
	public void createCreator() {
		creator = new PropertyCreator()
	}
	
	@Test
	public void preconditions() {
		assert creator != null
		assert Fabricator.factoryByName("kitten")
	}
	
	@Test
	public void createProperty_withParamThatIsAFactory_shouldReturnAssociation() {
		def property = creator.create("kitten")
		
		assert property != null
		assert property instanceof Association
		assert property.factory == Fabricator.factoryByName("kitten")
		assert property.count == 1
		assert property.closure == null
	}
	
	@Test
	public void createProperty_withParam_shouldReturnAssociation() {
		def property = creator.create("posts", [count:5])
		
		assert property != null
		assert property instanceof Association
		assert property.factory == Fabricator.factoryByName("posts")
		assert property.count == 5
		assert property.closure == null
	}

	@Test
	public void createProperty_withNoParamsAndNameIsASequence_shouldReturnSequenceProperty() {
		def property = creator.create("sequence")
		
		assert property != null
		assert property instanceof SequenceProperty
		assert property.sequence == Fabricator.sequenceByName("sequence")
	}

	
}
