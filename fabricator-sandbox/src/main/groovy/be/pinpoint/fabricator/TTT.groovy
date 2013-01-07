package be.pinpoint.fabricator

class TTT {
	static void define() {
		Fabricator.sequence("id")
		Fabricator.define(Customer) {
			id
			name "ttt"
		}
	}
}
