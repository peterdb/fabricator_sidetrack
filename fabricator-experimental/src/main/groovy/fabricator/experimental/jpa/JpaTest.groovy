package fabricator.experimental.jpa

import javax.persistence.Persistence

import fabricator.Fabricator
import fabricator.data.Email
import fabricator.experimental.jpa.entities.Address
import fabricator.experimental.jpa.entities.Person

def factory = Persistence.createEntityManagerFactory("fabricator-experimental-jpa-test")
def manager = factory.createEntityManager()

Fabricator.define { c ->
	afterCreate {
		manager.persist(it)
	}
	afterCreate {
		println "created $it"
	}

	c.factory(Address) {
		street "Busstraat 13"
		postalcode "9000"
		city "Gent"
	}
	
	c.factory(Person) {
		first "Peter"
		last "De Bruycker"
		email { Email.email(first, last) }
		address
	}
}

manager.getTransaction().begin()

def person = new Person(first: "Peter", last: "De Bruycker", email: "ttt@example.com")
manager.persist(person)

// persist a fabricated one
Fabricator.fabricate("person")
Fabricator.fabricate("person")

manager.getTransaction().commit()

factory.getPersistenceUnitUtil().

println manager.createQuery("select p from Person p").resultList
