package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import separator.DocumentUnitSeparatorFactory;
import separator.IDocumentUnitSeparator;

public class SearchEngine {

	/**
	 * Term id --> <document unit identifier, document unit frequency>
	 */
	private Map<String, Map<Integer, Integer>> termFrequency;

	/**
	 * Term id ---> number of documents that contain the term
	 */
	private Map<String, Integer> documentFrequency;

	private List<DocumentUnit> documentUnits;

	private IDocumentUnitSeparator documentUnitSeparator;

	private String input;

	public SearchEngine(String input) {
		this.input = input;
		termFrequency = new HashMap<String, Map<Integer, Integer>>();
		documentFrequency = new HashMap<String, Integer>();
		documentUnits = new ArrayList<DocumentUnit>();
		documentUnitSeparator = DocumentUnitSeparatorFactory
				.createDocumentUnitSeparator(input);
		tokenize();
	}

	/**
	 * Assign to each term in document unit weight from tf -idf scheme
	 */
	public void assignTfIdf() {
		Iterator<DocumentUnit> iterator = documentUnits.iterator();
		double documentNum = documentUnits.size();
		while (iterator.hasNext()) {
			DocumentUnit unit = iterator.next();
			int documentIndex = documentUnits.indexOf(unit);
			for (String key : unit.getTerms().keySet()) {
				int tf = termFrequency.get(key).get(documentIndex);
				double idf = Math.log(documentNum / documentFrequency.get(key));
				unit.getTerms().put(key, tf * idf);
			}
		}
	}

	public Map<String, Double> assignTfIdf(Set<String> query) {
		Map<String, Double> result = new HashMap<String, Double>();
		double documentNum = documentUnits.size();
		for (String key : query) {
			double frequency = 0;
			if (documentFrequency.containsKey(key.toLowerCase())) {
				int tf = 1;
				double idf = Math.log(documentNum
						/ documentFrequency.get(key.toLowerCase()));
				frequency = tf * idf;
			}
			result.put(key, frequency);
		}

		return result;
	}

	private void tokenize() {

		int documentUnitIndex = 0;
		DocumentUnit documentUnit = new DocumentUnit(
				new HashMap<String, Double>(), 0, 0);
		int startOfWord = 0;
		for (int i = 0; i < input.length() - 2; i++) {

			char ch = input.charAt(i);

			if (isWordSeparator(ch)) {
				if (startOfWord != i) {

					String word = input.substring(startOfWord, i).toLowerCase();

					if (!StopWords.isStopable(word)) {

						// adjust document frequency
						if (!documentUnit.getTerms().keySet().contains(word)) {
							if (!documentFrequency.containsKey(word)) {
								documentFrequency.put(word, 1);
							} else {
								documentFrequency.put(word, documentFrequency
										.get(word) + 1);
							}

							documentUnit.getTerms().put(word, 0.0);
						}

						// adjust term frequency
						if (!termFrequency.containsKey(word)) {
							HashMap<Integer, Integer> frequency = new HashMap<Integer, Integer>();
							frequency.put(documentUnitIndex, 1);
							termFrequency.put(word, frequency);
						} else {
							if (!termFrequency.get(word).containsKey(
									documentUnitIndex)) {
								termFrequency.get(word).put(documentUnitIndex,
										1);
							} else {
								termFrequency.get(word).put(
										documentUnitIndex,
										termFrequency.get(word).get(
												documentUnitIndex) + 1);
							}
						}
					}
				}
				startOfWord = i + 1;

			}

			// Check if it is end of paragraph
			if (documentUnitSeparator.isSeparator(i, input)) {
				if (!documentUnit.getTerms().isEmpty()) {
					documentUnit.setEnd(i);
					documentUnits.add(documentUnit);
					documentUnit = new DocumentUnit(
							new HashMap<String, Double>(), i + 2, 0);
					documentUnitIndex++;

				}
			}

		}
		if (!documentUnit.getTerms().isEmpty()) {
			documentUnit.setEnd(input.length() - 1);
			documentUnits.add(documentUnit);
		}

	}

	public static Set<String> stokanize(String input) {
		Set<String> res = new HashSet<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(input);
		while (stringTokenizer.hasMoreElements()) {
			String word = stringTokenizer.nextToken().toLowerCase();
			if (!StopWords.isStopable(word))
				res.add(word);
		}
		return res;
	}

	public List<DocumentUnit> getDocumentUnits() {
		return documentUnits;
	}

	public Map<String, Map<Integer, Integer>> getTermFrequency() {
		return termFrequency;
	}

	public Map<String, Integer> getDocumentFrequency() {
		return documentFrequency;
	}

	private boolean isWordSeparator(Character ch) {
		return !Character.isLetter(ch);
	}

}
