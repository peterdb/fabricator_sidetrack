package fabricator.support

import groovy.transform.ToString;

@ToString
class Story {
	String title
	User author
	Boolean published
	Date startAt
	Date endAt
}
