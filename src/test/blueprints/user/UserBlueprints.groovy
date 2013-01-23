package user
import fabricator.support.Kitten
import fabricator.support.User


User.blueprint {
	ignore {
		host "example.com"
	}
	
	first "John"
	last "Doe"
	email { "$first.$last@$host" }
}

User.blueprint("admin") {
	admin true
}