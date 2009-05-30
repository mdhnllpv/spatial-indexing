package tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	private List<Map<String, Double>> documentUnits;

	public TokenizerImpl() {

		wordSeparator = new ArrayList<Character>();
		wordSeparator.add('\n');
		wordSeparator.add(' ');
		wordSeparator.add('\t');
		wordSeparator.add('\r');
		wordSeparator.add(',');
		wordSeparator.add('.');
		wordSeparator.add(':');
		wordSeparator.add(';');
		wordSeparator.add('?');
		wordSeparator.add('!');
		wordSeparator.add('(');
		wordSeparator.add(')');

		documentUnitSeparator = new ArrayList<Character>();
		documentUnitSeparator.add('\t');

		termFrequency = new HashMap<String, Map<Integer, Integer>>();

		documentFrequency = new HashMap<String, Integer>();

		documentUnits = new ArrayList<Map<String, Double>>();
	}

	/**
	 * Assign to each term in document unit weight from tf -idf scheme
	 */
	public void assignTfIdf() {
		Iterator<Map<String, Double>> iterator = documentUnits.iterator();
		double documentNum = documentUnits.size();
		while (iterator.hasNext()) {
			Map<String, Double> unit = iterator.next();
			int documentIndex = documentUnits.indexOf(unit);
			for (String key : unit.keySet()) {
				int tf = termFrequency.get(key).get(documentIndex);
				double idf = Math.log(documentNum / documentFrequency.get(key));
				unit.put(key, tf * idf);
			}
		}
	}

	public Map<String, Double> assignTfIdf(Set<String> query) {
		Map<String, Double> result = new HashMap<String, Double>();
		double documentNum = documentUnits.size();
		for (String key : query) {
			int tf = 1;
			double idf = Math.log(documentNum / documentFrequency.get(key));
			result.put(key, tf * idf);
		}

		return result;
	}

	public void tokenize(String input) {

		int documentUnitIndex = 0;
		HashMap<String, Double> documentUnit = new HashMap<String, Double>();
		int startOfWord = 0;
		for (int i = 0; i < input.length() - 2; i++) {

			char ch = input.charAt(i);

			if (ch == '\n' && input.charAt(i + 2) == '\n') {
				if (!documentUnit.isEmpty()) {
					documentUnits.add(documentUnit);
					documentUnit = new HashMap<String, Double>();
					documentUnitIndex++;
				}
			}

			if (wordSeparator.contains(ch)) {
				if (startOfWord != i) {

					String word = input.substring(startOfWord, i).toLowerCase();

					// adjust document frequency
					if (!documentUnit.keySet().contains(word)) {
						if (!documentFrequency.containsKey(word)) {
							documentFrequency.put(word, 1);
						} else {
							documentFrequency.put(word, documentFrequency
									.get(word) + 1);
						}

						documentUnit.put(word, 0.0);
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
				startOfWord = i + 1;

			}

		}
		if (!documentUnit.isEmpty()) {
			documentUnits.add(documentUnit);
			documentUnit = new HashMap<String, Double>();
		}

	}

	public List<Map<String, Double>> getDocumentUnits() {
		return documentUnits;
	}

	public Map<String, Map<Integer, Integer>> getTermFrequency() {
		return termFrequency;
	}

	public Map<String, Integer> getDocumentFrequency() {
		return documentFrequency;
	}

}
