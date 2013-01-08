package fabricator;

import fabricator.Fabricator;
import fabricator.TTT;

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
