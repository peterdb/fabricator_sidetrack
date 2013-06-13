package fabricator.dsl

import fabricator.Definition;
import fabricator.Trait;
import fabricator.declaration.AssociationDeclaration
import fabricator.declaration.DynamicDeclaration
import fabricator.declaration.ImplicitDeclaration
import fabricator.declaration.StaticDeclaration

class DefinitionProxy {

	@Delegate
	final Definition definition
	final List childFactories = []
	final boolean ignore

	public DefinitionProxy(Definition definition, boolean ignore = false) {
		this.definition = definition;
		this.ignore = ignore
	}

	def propertyMissing(String name) {
		definition.declareProperty(new ImplicitDeclaration(name, ignore))
	}

	/**
	 * Calls addProperty using the missing method name as the name of the
	 * attribute, so that:
	 *
	 * <pre>
	 * factory(User) {
	 *     name "Billy Idol"
	 * }
	 * </pre>
	 *
	 * and:
	 *
	 * <pre>
	 * factory(User) {
	 *     addProperty "name", "Billy Idol"
	 * }
	 * </pre>
	 *
	 * are equivalent.
	 *
	 * If no argument or block is given, factory_girl will look for a sequence
	 * or association with the same name. This means that:
	 *
	 *   factory :user do
	 *     email { create(:email) }
	 *     association :account
	 *   end
	 *
	 * and:
	 *
	 *   factory :user do
	 *     email
	 *     account
	 *   end
	 *
	 * are equivalent.
	 */
	def methodMissing(String name, args) {
		if(args.size() == 0) {
			definition.declareProperty(new ImplicitDeclaration(name, ignore))
		} else if(args[0] instanceof Map && args[0].containsKey("factory") ) {
			association(name, args)
		} else {
			addProperty(name, args[0])
		}
	}

	/** 
	 * Adds an attribute that should be assigned on generated instances for this
	 * factory.
	 *
	 * This method should be called with either a value or block, but not both. If
	 * called with a block, the attribute will be generated "lazily," whenever an
	 * instance is generated. Lazy attribute blocks will not be called if that
	 * attribute is overridden for a specific instance.
	 *
	 * When defining lazy attributes, an instance of FactoryGirl::Strategy will
	 * be yielded, allowing associations to be built using the correct build
	 * strategy.
	 *
	 * Arguments:
	 * * name: +Symbol+ or +String+
	 *   The name of this attribute. This will be assigned using "name=" for
	 *   generated instances.
	 * * value: +Object+
	 *   If no block is given, this value will be used for this attribute.
	 */
	public void addProperty(String name, Object value){
		definition.declareProperty(new StaticDeclaration(name, value, ignore))
	}

	public void addProperty(String name, Closure closure){
		definition.declareProperty(new DynamicDeclaration(name, ignore, closure))
	}
	
	/** Adds an attribute that builds an association. The associated instance will
	 * be built using the same build strategy as the parent instance.
	 *
	 * Example:
	 *   factory :user do
	 *     name 'Joey'
	 *   end
	 *
	 *   factory :post do
	 *     association :author, factory: :user
	 *   end
	 *
	 * Arguments:
	 * * name: +Symbol+
	 *   The name of this attribute.
	 * * options: +Hash+
	 *
	 * Options:
	 * * factory: +Symbol+ or +String+
	 *    The name of the factory to use when building the associated instance.
	 *    If no name is given, the name of the attribute is assumed to be the
	 *    name of the factory. For example, a "user" association will by
	 *    default use the "user" factory.
	 */
	def association(name, options) {
		definition.declareProperty(new AssociationDeclaration(name, options))
	}

	// for creating transient properties
	def ignore(Closure closure) {
		new DefinitionProxy(definition, true).with(closure)
	}
	
	def factory(Map options = [:], String name) {
		factory(options, name, null)
	}
	
	def factory(Map options = [:], String name, Closure closure) {
		childFactories << [name, options, closure]
	}
	
	def trait(String name, Closure closure) {
		Trait trait = new Trait(name)
		
		new DefinitionProxy(trait.definition).with(closure)
		
		definition.defineTrait(trait)
	}
	
	def instantiator(Closure closure) {
		instantiator = closure
	}
}