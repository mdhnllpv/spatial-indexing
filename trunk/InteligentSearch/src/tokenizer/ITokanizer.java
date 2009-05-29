package tokenizer;

import java.util.Collection;

public interface ITokanizer {
	/**
	 * Tokenize the input
	 * 
	 * @param input
	 *            input string
	 * @return collection of tonezined document units
	 */
	Collection<Collection<String>> tokenize(String input);
}
