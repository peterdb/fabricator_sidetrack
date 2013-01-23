package fabricator.support

import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.*
import org.codehaus.groovy.classgen.*
import java.security.CodeSource

"abc".toLowerCase_()

// example script to analyze
def scriptText = '''
	class User {
		static def hello(world) {
			println "hello $world"
		}
	}

   	User.mmmm("hello world")
	User.mmmm!("hello world!")
'''

// define a visitor that visits all variable expressions
class VariableVisitor extends ClassCodeVisitorSupport {
   @Override
	public void visitMethodCallExpression(MethodCallExpression call) {
		println call
		println call.objectExpression
		
		super.visitMethodCallExpression(call)
	}

   protected SourceUnit getSourceUnit() {
       return source;
   }
}

// we define our custom PrimaryClassNodeOperation
// to be able to hook our code visitor
class CustomSourceOperation extends CompilationUnit.PrimaryClassNodeOperation {
   CodeVisitorSupport visitor
   void call(SourceUnit source, GeneratorContext context, ClassNode
classNode) throws CompilationFailedException {
       classNode.visitContents(visitor)
   }
}

// we use our own class loader to add our phase operation
class MyClassLoader extends GroovyClassLoader {
   CodeVisitorSupport visitor
   protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source)
{
       CompilationUnit cu = super.createCompilationUnit(config, source)
       cu.addPhaseOperation(new CustomSourceOperation(visitor:
visitor), Phases.CLASS_GENERATION)
       return cu
   }
}

def visitor =  new VariableVisitor()
def myCL = new MyClassLoader(visitor: visitor)
// simply by parsing the script with our classloader
// our visitor will be called and will visit all the variables
def script = myCL.parseClass(scriptText)

//println "Bound variables:   ${visitor.bound}"
//println "Unbound variables: ${visitor.unbound}"
