package fabricator.support

import fabricator.Fabricator
import fabricator.experimental.FabricatorGroovyMethods;

class TTT {

	public static void main(String[] args) {
		Fabricator.define {
			sequence("ttt")
			sequence("first") { n -> "person$n" }
			sequence("first2", start: 10) { n -> "person$n" }
			
			factory(Kitten) {
				name "Garfield"
			}
			
			factory(User) {
				ignore {
					host "example.com"
				}
				
				first
				last "dynamic"
				email { "$first.$last@$host" }
				kitten
				
				factory("admin") {
					admin true
				}
				
				factory("admin2", parent: "admin") {
					first "jup"
					admin true
				}
			}
		}
		
		println Fabricator.fabricate(User)
		println Fabricator.fabricate(User, admin: true)
		println Fabricator.fabricate("admin")
		println Fabricator.fabricate("admin2")
		
		use(FabricatorGroovyMethods) {
			println User.fabricate()
		}
	}
	
}
