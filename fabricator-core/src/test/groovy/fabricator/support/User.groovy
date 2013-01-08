package fabricator.support

import groovy.transform.ToString;

@ToString
class User {
	String first
	String last
	String email
	boolean admin
	List posts
	Kitten kitten
}
