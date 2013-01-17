package fabricator.support

import static org.junit.Assert.*

import org.junit.Test

class NamedTest {

	@Test
	public void names_withoutAliases() {
		def named = new Named("name")
		
		assert named.names() == ["name"]
	}
	
	@Test
	public void names_withAliases() {
		def named = new Named("name", ["alias1", "alias2"])
		
		assert named.names() == ["alias1", "alias2", "name"]
	}
	
}
