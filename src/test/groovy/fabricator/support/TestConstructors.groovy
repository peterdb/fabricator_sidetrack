package fabricator.support

class TestConstructors {
	public static void main(String[] args) {
		println Company.class.getConstructors()
		
		/* strategy:
		 * 1. find constructor with least parameters
		 * 2. for each param -> find a property in map with the type (in order of appearance)
		 * 3. use these params for calling the constructor
		 * 4. try to pass null for missing parameters
		 */
		
		println Company.class.newInstance()
		println Company.class.newInstance("test")
		println Company.class.newInstance(name: "test")
	}
}
