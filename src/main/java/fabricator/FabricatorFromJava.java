package fabricator;


public class FabricatorFromJava {
	public static void main(String[] args) {
		Fabricator.registerFactory(new Factory("user"));
		
		System.out.println(Fabricator.fabricate("user"));
	}
}
