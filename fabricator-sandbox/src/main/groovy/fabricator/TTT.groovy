package fabricator

import fabricator.Customer;
import fabricator.Fabricator;

class TTT {
	static void define() {
		Fabricator.sequence("id")
		Fabricator.define(Customer) {
			id
			name "ttt"
		}
	}
}
