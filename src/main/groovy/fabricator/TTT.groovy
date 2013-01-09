package fabricator

class TTT {
	final Factory factory
	
	public TTT(Factory factory) {
		this.factory = factory
	}
	
	def propertyMissing(String name) {
		println "jup"
		
		return factory.methodMissing(name, ignore: true)
	}

	def methodMissing(String name, args) {
		println "jup2"
		return factory.methodMissing(name, args, ignore: true)
	}
}
