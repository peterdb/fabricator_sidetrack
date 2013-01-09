package fabricator.support

class CustomIncrementable {

	final int value
	
	public CustomIncrementable(int value) {
		this.value = value
	}
	
	CustomIncrementable next() {
		new CustomIncrementable(value + 1)
	}
	
}
