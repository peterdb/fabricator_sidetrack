package fabricator.support

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import groovy.util.logging.Log;

@ToString
class User {
	String first
	String last
	String email
	boolean admin
	List posts
	Kitten kitten
}
