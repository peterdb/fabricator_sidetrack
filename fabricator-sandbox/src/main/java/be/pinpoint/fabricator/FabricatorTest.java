package be.pinpoint.fabricator;

public class FabricatorTest {
	public static void main(String[] args) {
		TTT.define();
		
		System.out.println(Fabricator.fabricate("customer"));
		System.out.println(Fabricator.fabricate("customer"));
		
		System.out.println(Fabricator.generate("id"));
		System.out.println(Fabricator.generate("id"));
		
		System.out.println(Fabricator.fabricate("customer"));
		System.out.println(Fabricator.fabricate("customer"));

		System.out.println(Fabricator.generate("id"));
	}
}
