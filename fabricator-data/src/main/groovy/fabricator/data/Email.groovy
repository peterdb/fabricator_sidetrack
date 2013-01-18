package fabricator.data

/**
 * Generate email addresses from a given name
 * 
 * @author peter
 */
class Email {

	static email(String first, String last, String host = "example.com") {
		def f = first.toLowerCase().replaceAll("\\s", "_")
		def l = last.toLowerCase().replaceAll("\\s", "_")
		
		return "$f.$l@$host"
	}
	
}
