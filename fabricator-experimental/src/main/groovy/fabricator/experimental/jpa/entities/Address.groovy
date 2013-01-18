package fabricator.experimental.jpa.entities

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
@EqualsAndHashCode
class Address {

	@Id @GeneratedValue
	Long id
	
	String street
	String postalcode
	String city
	
}
