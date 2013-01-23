package fabricator.support

import fabricator.Fabricator
import groovy.io.FileType

//Fabricator.configure2 { config ->
//	config.blueprintPath = 'src/test/blueprints'
//}
//Fabricator.load()

def blueprintsPath = 'src/test/blueprints'

def blueprints = []
File dir = new File(blueprintsPath)

if(dir.exists()) {
	dir.eachFileRecurse(FileType.FILES) { file ->
		if(file.name.endsWith("Blueprints.groovy")) {
			ClassLoader parent = getClass().getClassLoader();
			GroovyClassLoader loader = new GroovyClassLoader(parent);
	
			println "test"
			Class groovyClass = loader.parseClass(file);
			groovyClass.invokeMethod("main", null)
		}
	}
}

println User.build()
println User.build("admin")
