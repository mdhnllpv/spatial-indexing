package tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TokenizerImpl {

	private List<Character> documentUnitSeparator;

	private List<Character> wordSeparator;

	/**
	 * Term id --> <document unit identifier, document unit frequency>
	 */
	private Map<String, Map<Integer, Integer>> termFrequency;

	/**
	 * Term id ---> number of documents that contain the term
	 */
	private Map<String, Integer> documentFrequency;

	private List<Set<String>> documentUnits;

	public TokenizerImpl() {

		wordSeparator = new ArrayList<Character>();
		wordSeparator.add('\n');
		wordSeparator.add(' ');
		wordSeparator.add('\t');

		documentUnitSeparator = new ArrayList<Character>();
		documentUnitSeparator.add('\t');

		termFrequency = new HashMap<String, Map<Integer, Integer>>();

		documentFrequency = new HashMap<String, Integer>();

		documentUnits = new ArrayList<Set<String>>();
	}

	public void tokenize(String input) {

		int documentUnitIndex = 0;
		HashSet<String> documentUnit = new HashSet<String>();

		int left = 0;
		int right = 0;
		for (int i = 0; i < input.length(); i++) {

			char ch = input.charAt(i);

			if (wordSeparator.contains(ch)) {
				if (left != right) {

					String word = input.substring(left, right);

					// adjust document frequency
					if (!documentUnit.contains(word)) {
						if (!documentFrequency.containsKey(word)) {
							documentFrequency.put(word, 1);
						} else {
							documentFrequency.put(word, documentFrequency
									.get(word) + 1);
						}

						documentUnit.add(word);
					}

					// adjust term frequency
					if (!termFrequency.containsKey(word)) {
						HashMap<Integer, Integer> frequency = new HashMap<Integer, Integer>();
						frequency.put(documentUnitIndex, 1);
						termFrequency.put(word, frequency);
					} else {
						if (!termFrequency.get(word).containsKey(
								documentUnitIndex)) {
							termFrequency.get(word).put(documentUnitIndex, 1);
						} else {
							termFrequency.get(word).put(
									documentUnitIndex,
									termFrequency.get(word).get(
											documentUnitIndex) + 1);
						}
					}
				}
				left = right = i + 1;

			} else {
				right++;
			}

			if (documentUnitSeparator.contains(ch)) {
				if (!documentUnit.isEmpty()){
					documentUnits.add(documentUnit);
					documentUnit = new HashSet<String>();
					documentUnitIndex++;
				}
			}

		}
		if (!documentUnit.isEmpty()){
			documentUnits.add(documentUnit);
			documentUnit = new HashSet<String>();
		}
		
	}

	public List<Set<String>> getDocumentUnits() {
		return documentUnits;
	}

	public Map<String, Map<Integer, Integer>> getTermFrequency() {
		return termFrequency;
	}

	public Map<String, Integer> getDocumentFrequency() {
		return documentFrequency;
	}

}
