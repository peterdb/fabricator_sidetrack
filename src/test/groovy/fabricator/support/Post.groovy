package fabricator.support

import groovy.transform.ToString;

@ToString
class Post {
	User author
	String title
	String content
}
