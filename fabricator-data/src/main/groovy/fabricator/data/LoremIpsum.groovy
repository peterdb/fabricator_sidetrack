package fabricator.data


class LoremIpsum {

	static final LOREM_IPSUM = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
 
	static final WORDS = ['exercitationem', 'perferendis', 'perspiciatis', 'laborum', 'eveniet',
	    'sunt', 'iure', 'nam', 'nobis', 'eum', 'cum', 'officiis', 'excepturi',
	    'odio', 'consectetur', 'quasi', 'aut', 'quisquam', 'vel', 'eligendi',
	    'itaque', 'non', 'odit', 'tempore', 'quaerat', 'dignissimos',
	    'facilis', 'neque', 'nihil', 'expedita', 'vitae', 'vero', 'ipsum',
	    'nisi', 'animi', 'cumque', 'pariatur', 'velit', 'modi', 'natus',
	    'iusto', 'eaque', 'sequi', 'illo', 'sed', 'ex', 'et', 'voluptatibus',
	    'tempora', 'veritatis', 'ratione', 'assumenda', 'incidunt', 'nostrum',
	    'placeat', 'aliquid', 'fuga', 'provident', 'praesentium', 'rem',
	    'necessitatibus', 'suscipit', 'adipisci', 'quidem', 'possimus',
	    'voluptas', 'debitis', 'sint', 'accusantium', 'unde', 'sapiente',
	    'voluptate', 'qui', 'aspernatur', 'laudantium', 'soluta', 'amet',
	    'quo', 'aliquam', 'saepe', 'culpa', 'libero', 'ipsa', 'dicta',
	    'reiciendis', 'nesciunt', 'doloribus', 'autem', 'impedit', 'minima',
	    'maiores', 'repudiandae', 'ipsam', 'obcaecati', 'ullam', 'enim',
	    'totam', 'delectus', 'ducimus', 'quis', 'voluptates', 'dolores',
	    'molestiae', 'harum', 'dolorem', 'quia', 'voluptatem', 'molestias',
	    'magni', 'distinctio', 'omnis', 'illum', 'dolorum', 'voluptatum', 'ea',
	    'quas', 'quam', 'corporis', 'quae', 'blanditiis', 'atque', 'deserunt',
	    'laboriosam', 'earum', 'consequuntur', 'hic', 'cupiditate',
	    'quibusdam', 'accusamus', 'ut', 'rerum', 'error', 'minus', 'eius',
	    'ab', 'ad', 'nemo', 'fugit', 'officia', 'at', 'in', 'id', 'quos',
	    'reprehenderit', 'numquam', 'iste', 'fugiat', 'sit', 'inventore',
	    'beatae', 'repellendus', 'magnam', 'recusandae', 'quod', 'explicabo',
	    'doloremque', 'aperiam', 'consequatur', 'asperiores', 'commodi',
	    'optio', 'dolor', 'labore', 'temporibus', 'repellat', 'veniam',
	    'architecto', 'est', 'esse', 'mollitia', 'nulla', 'a', 'similique',
	    'eos', 'alias', 'dolore', 'tenetur', 'deleniti', 'porro', 'facere',
	    'maxime', 'corrupti']
 
	static final LOREM_IPSUM_WORDS
 
	static {
		LOREM_IPSUM_WORDS = LOREM_IPSUM.toLowerCase().split("\\W+")
	}

   /**
    * Returns a randomly generated sentence of lorem ipsum text.
    * <p>
    * A sentence exists of 1 to 4 sections, separated by commas. Sections exist of 3 to 12 words.
    * <p>
    * The first word is capitalized, and the sentence ends in either a period or
    * question mark.
    */
	static sentence() {
		Random rand = new Random()
		
	    //Determine the number of comma-separated sections and number of words in each section for this sentence.
		def sections = (0..rand.nextInt(4)).collect {
	        words(rand.nextInt(9) + 3, false)    
	    }
		
	    // Convert to sentence case and add end punctuation.
		return (sections.join(', ') + (rand.nextBoolean() ? '.' : '?')).capitalize()
	}
 
   /**
    * Returns a randomly generated paragraph of lorem ipsum text.
    * The paragraph consists of between 1 and 4 sentences, inclusive.
    */
	static paragraph() {
		Random rand = new Random()

		def sentences = []
		
		(rand.nextInt(4) + 1).times {
			sentences << sentence()
		}
	       
	    return sentences.join(' ')
	}
 

    /**
    * Returns a list of paragraphs as returned by paragraph().
    *
    * If `common` is True, then the first paragraph will be the standard
    * 'lorem ipsum' paragraph. Otherwise, the first paragraph will be random
    * Latin text. Either way, subsequent paragraphs will be random Latin text.
    */
	static paragraphs(int count, boolean isCommon = true) {
		def paragraphs = (0..<count).collect { n -> 
			if(isCommon && n == 0) {
				LOREM_IPSUM
			} else {
				paragraph()
			}
		}
	           
	    return paragraphs.join('\n')
	}
 
   /**
    * Returns a string of <code>count</code> lorem ipsum words separated by a single space.
    *
    * If `common` is True, then the first 19 words will be the standard
    * 'lorem ipsum' words. Otherwise, all words will be selected randomly.
    */
	static words(int count, boolean isCommon = true) {
		def result = []
		
	    if (isCommon) {
	        if (count > LOREM_IPSUM_WORDS.size()) {
				result = LOREM_IPSUM_WORDS + words(count - LOREM_IPSUM_WORDS.size(), false)
	        }
	        else {
	            result = LOREM_IPSUM_WORDS[0..count-1];
	        }
	    }
	    else {
			Random rand = new Random()
			result = (0..<count).collect { WORDS[rand.nextInt(WORDS.size())] }
	    }
	   
	    return result.join(' ')
	}

	public static main(def args) {
		println words(5, false)
		println words(2, true)
		println sentence()
		println paragraph()
		println "start"
		println paragraphs(2, false)
		println "2"
		println paragraphs(5, true)
		
		assert words(10, false).split(' ').size() == 10
		assert words(5, true).split(' ').size() == 5
		assert words(10, true) == 'lorem ipsum dolor sit amet consectetur adipisicing elit sed do'
	}
}
