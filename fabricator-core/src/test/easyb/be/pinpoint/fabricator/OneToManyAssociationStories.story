package be.pinpoint.fabricator

import be.pinpoint.fabricator.support.Kitten
import be.pinpoint.fabricator.support.Post
import be.pinpoint.fabricator.support.User

description "one-to-many association stories"

scenario "a simple one-to-many association", {
	given "a user def with a posts association", {
		Fabricator.sequence("title", { n -> "title $n" })
		Fabricator.define("posts", Post) {
			title
			content "Lorem ipsum"
		}
		Fabricator.define(User) { 
			first "John"
			last "Doe"
			posts(count: 5)
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have 5 posts", {
		user.posts.size.shouldBe 5
		user.posts.eachWithIndex { post, n ->
			post.title.shouldBe "title $n"
			post.content.shouldBe "Lorem ipsum"
		}
	}
}

scenario "a one-to-many association with overridden property", {
	given "a user def with a posts association", {
		Fabricator.sequence("title", { n -> "title $n" })
		Fabricator.define("posts", Post) {
			title
			content "Lorem ipsum"
		}
		Fabricator.define(User) {
			first "John"
			last "Doe"
			posts(count: 2, content: "overridden content")
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have posts", {
		user.posts.size.shouldBe 2
		user.posts.eachWithIndex { post, n ->
			post.title.shouldBe "title $n"
			post.content.shouldBe "overridden content"
		}
	}
}

scenario "a one-to-many association with a closure", {
	given "a user def with a posts association", {
		Fabricator.sequence("title", { n -> "title $n" })
		Fabricator.define(Post) {
			title
			content "Lorem ipsum"
		}
		Fabricator.define(User) {
			first "John"
			last "Doe"
			posts(count: 3, Post) { n ->
				title "title - $n"
			}
		}
	}
	
	when "a user is fabricated", {
		user = Fabricator.fabricate(User)
	}
	
	then "it should have 3 posts", {
		user.posts.size.shouldBe 3
		user.posts.eachWithIndex { post, n ->
			post.title.shouldBe "title - $n"
			post.content.shouldBe "Lorem ipsum"
		}
	}
}