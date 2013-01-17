package fabricator.stories.experimental

import fabricator.Fabricator
import fabricator.experimental.FabricatorGroovyMethods
import fabricator.support.User

description "fabricator methods -> Class.fabricate(...)"

before "factory the factories", {
	given "factories are factoryd", {
		Fabricator.define {
			factory(User, aliases: ["john"]) {
				first "John"
				last "Doe"
				email { "$first.$last@example.com" }
				
				factory("jane") { first "Jane" }
			}
		}
	}
}

scenario "using a category", {
	given "fabricated users", {
		use(FabricatorGroovyMethods) {
			user1 = User.fabricate()
			user2 = User.fabricate(admin: true)
			user3 = User.fabricate("jane")
			user4 = User.fabricate("jane", admin: true)
		}
	}

	then "it should be populated", {
		ensure(user1){
			isAUser
			has(first:"John", last:"Doe", email:"John.Doe@example.com")
			has(admin: false)
		}

		ensure(user2){
			isAUser
			has(first:"John", last:"Doe", email:"John.Doe@example.com")
			has(admin:true)
		}

		ensure(user3){
			isAUser
			has(first:"Jane", last:"Doe", email:"Jane.Doe@example.com")
			has(admin:false)
		}

		ensure(user4){
			isAUser
			has(first:"Jane", last:"Doe", email:"Jane.Doe@example.com")
			has(admin:true)
		}
	}
}

scenario "using metaClass", {
	given "fabricated users", {
		FabricatorGroovyMethods.install()
		
		user1 = User.fabricate()
		user2 = User.fabricate(admin: true)
		user3 = User.fabricate("jane")
		user4 = User.fabricate("jane", admin: true)
	}

	then "it should be populated", {
		ensure(user1){
			isAUser
			has(first:"John", last:"Doe", email:"John.Doe@example.com")
			has(admin: false)
		}

		ensure(user2){
			isAUser
			has(first:"John", last:"Doe", email:"John.Doe@example.com")
			has(admin:true)
		}

		ensure(user3){
			isAUser
			has(first:"Jane", last:"Doe", email:"Jane.Doe@example.com")
			has(admin:false)
		}

		ensure(user4){
			isAUser
			has(first:"Jane", last:"Doe", email:"Jane.Doe@example.com")
			has(admin:true)
		}
	}
}
