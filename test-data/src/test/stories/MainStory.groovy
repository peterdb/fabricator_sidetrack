import ttt.Factory


scenario "ttt", {
	given "a factory",{
		name = "user"
		klass = User
		factory = new Factory(name, klass)
		//FactoryGirl.registerFactory(factory)
	}

	then "it has a factory name", { factory.name.shouldBe name  }


	and "it has a build class", {
		factory.klass.shouldBe klass
	}
}