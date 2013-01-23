package fabricator

import groovy.io.FileType

class BlueprintLoader {
	static load(File blueprintsPath) {
		assert blueprintsPath, "blueprintsPath cannot be null"

		def blueprints = []
		File dir = new File(blueprintsPath)

		if(dir.exists()) {
			ClassLoader parent = Fabricator.getClassLoader()
			GroovyClassLoader loader = new GroovyClassLoader(parent)

			dir.eachFileRecurse(FileType.FILES) { file ->
				if(file.name.endsWith("Blueprints.groovy")) {
					load(loader, file)
				}
			}
		}
	}
	
	private static void load(GroovyClassLoader classLoader, File blueprintFile) {
		Class groovyClass = classLoader.parseClass(blueprintFile)
		groovyClass.invokeMethod("main", null)
	}
}
