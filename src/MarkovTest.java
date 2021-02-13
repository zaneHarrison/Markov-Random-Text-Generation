import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.*;

public class MarkovTest {
	protected static String PSEUDO_EOS = "";

	private MarkovInterface<String> getModel(int order) {
		// TODO: change to EfficientMarkov
		return new BaseMarkov(order);
	}
	

	/**
	 * This test checks if MarkovModel makes a correct " Ngram using a simple
	 * source
	 */
	@Test
	public void testMapMakeNgram() {
		
		final String randText = assertTimeout(Duration.ofMillis(10000),()->{
			MarkovInterface<String> markov = getModel(2);
			markov.setTraining("aabbaabbaabbaabbaabbaabbaabbaabbaabbaabba"
					+ "abbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb");
			String output = "";
			while (output.length() < 8) {
				output = markov.getRandomText(100);
			}
			return output;
		});
		Assertions.assertAll("get random output",
			()->assertFalse(randText.contains("aaa"),"This test checks if MarkovModel makes a correct " +
					"Ngram using a simple source"),
			()->assertFalse(randText.contains("bbb"),"This test checks if MarkovModel makes a correct " +
					"Ngram using a simple source"),
			()->assertFalse(randText.contains("aba"),"This test checks if MarkovModel makes a correct " + 
					"Ngram using a simple source"),
			()->assertFalse(randText.contains("bab"),"This test checks if MarkovModel makes a correct " + 
					"Ngram using a simple source"),
			()->assertTrue(randText.contains("aab"),"This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source"),
			()->assertTrue(randText.contains("baa"),"This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source"),
			()->assertTrue(randText.contains("abb"),"This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source"),
			()->assertTrue(randText.contains("bba"),"This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source"));
		

	}

	/**
	 * This test checks if MarkovModel makes a correct " Ngram when the source
	 * contains only one distinct letter
	 */
	@Test
	public void testMapAllRepeats() {
		
		final String randText = assertTimeout(Duration.ofMillis(10000),()->{
			String testString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			MarkovInterface<String> markov = getModel(1);
			markov.setTraining(testString);
			String output = "";
			while (output.length() < 8) {
				output = markov.getRandomText(testString.length());
			}
			return output;
		});
		assertEquals(randText, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".substring(0, randText.length()),
				    "This test checks if MarkovModel makes a correct " + 
					 "Ngram when the source contains only one distinct letter"
					 );
	}

	/**
	 * This test checks if MarkovModel makes a correct Ngram when the source
	 * contains no repeat letters
	 */
	@Test
	public void testMapNoRepeats() {
		String testString = "qwertyuiopasdfghjklzxcvbnm";
		final String randText = assertTimeout(Duration.ofMillis(10000),()->{
	
			MarkovInterface<String> markov = getModel(1);
			markov.setTraining(testString);
			String output = markov.getRandomText(100);
			return output;
		});
		assertTrue(testString.contains(randText),
				"This test checks if MarkovModel makes a correct " + 
				"Ngram when the source contains no repeat letters"
				);
	}

	/**
	 * This test runs getFollows on every character and checks if only the last k-gram
	 * contains PSEUDO_EOS for a simple case.
	 */
	@Test
	public void testPSEUDO_EOS() {
		String testString = "aaabbbaaabbb";
		assertTimeout(Duration.ofMillis(10000),()->{

			MarkovInterface<String> markov = getModel(2);
			markov.setTraining(testString);
			assertFalse(markov.getFollows("aa").contains(PSEUDO_EOS),
					"This test checks if a PSEUDO_EOS character is added " +
							"only after the last k-gram in a given training string."
			);
			assertFalse(markov.getFollows("ab").contains(PSEUDO_EOS),
					"This test checks if a PSEUDO_EOS character is added " +
							"only after the last k-gram in a given training string."
			);
			assertFalse(markov.getFollows("ba").contains(PSEUDO_EOS),
					"This test checks if a PSEUDO_EOS character is added " +
							"only after the last k-gram in a given training string."
			);
			assertTrue(markov.getFollows("bb").contains(PSEUDO_EOS),
					"This test checks if a PSEUDO_EOS character is added " +
							"only after the last k-gram in a given training string."
			);
		});


	}

}
