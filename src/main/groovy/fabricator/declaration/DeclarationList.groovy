package fabricator.declaration

class DeclarationList {

	@Delegate
	private final List declarations = []

	public List toProperties() {
		declarations*.toProperty()
	}
}
