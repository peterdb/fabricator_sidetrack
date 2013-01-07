import org.junit.Ignore;

import be.pinpoint.fabricator.Fabricator
import be.pinpoint.fabricator.support.Kitten
import be.pinpoint.fabricator.support.Post
import be.pinpoint.fabricator.support.User


@Ignore
class Test {
	public static void main(String[] args) {
		Fabricator.define("posts", Post) {
			title Fabricator.sequence("title ", { n -> "title $n" })
		}
		
		Fabricator.define(Kitten)
		
		Fabricator.sequence("email", { n -> "person$n@gmail.com" })
		Fabricator.define(User) {
			first "John"
			last { first }
			email // will use the sequence with the same name
			kitten name: "Garfield"
		}
		Fabricator.define("user2", User) {
			first "John"
			last Fabricator.sequence("last", { n -> "Doe$n" })
			email { "$first.$last@gmail.com" }
		}
		Fabricator.define("admin", from: User) {
			admin true
			posts(count:2) { n -> Fabricator.fabricate("posts", content: {"content $n"}) }
		}
		Fabricator.define("admin2", from: "admin") {
			last "Wayne"
			posts(count:5)
		}

		println Fabricator.fabricate("user")
		println Fabricator.fabricate("user2")
		println Fabricator.fabricate("user2", first: "ttt")
		println Fabricator.fabricate("user2", last: { first.toUpperCase() })
		println Fabricator.fabricate("admin")
		println Fabricator.fabricate("admin2")
	}
}
