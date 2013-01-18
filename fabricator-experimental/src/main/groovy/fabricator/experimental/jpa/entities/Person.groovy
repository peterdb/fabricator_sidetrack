package fabricator.experimental.jpa.entities

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@ToString
@EqualsAndHashCode
class Person {

	@Id @GeneratedValue
	Long id
	
	String first
	String last
	
	String email
	
	@ManyToOne
	Address address
	
}
