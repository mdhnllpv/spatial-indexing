package query;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import scoring.DocumentScorrer;
import tokenizer.DocumentUnit;
import tokenizer.TokenizerImpl;

public class QueryProcessor {
	private TokenizerImpl tokanizator;

	public QueryProcessor(TokenizerImpl tokenizer) {
		this.tokanizator = tokenizer;
	}

	public void answer(Set<String> query) {
		final Map<String, Double> queryVector = tokanizator.assignTfIdf(query);
		Collections.sort(this.tokanizator.getDocumentUnits(),
				new Comparator<DocumentUnit>() {

					@Override
					public int compare(DocumentUnit o1, DocumentUnit o2) {
						if (DocumentScorrer.cosine(queryVector, o1.getTerms()) < DocumentScorrer
								.cosine(queryVector, o2.getTerms())) {
							return 1;
						}
						return -1;
					}

				});
	}

	public DocumentUnit singleAnswer(Set<String> query) {
		DocumentUnit unit = null;
		final Map<String, Double> queryVector = tokanizator.assignTfIdf(query);
		unit = Collections.max(tokanizator.getDocumentUnits(), new Comparator<DocumentUnit>(){

			@Override
			public int compare(DocumentUnit o1, DocumentUnit o2) {
				if (DocumentScorrer.cosine(queryVector, o1.getTerms()) < DocumentScorrer
						.cosine(queryVector, o2.getTerms())) {
					return 1;
				}
				return -1;
			}
			
		});
		return unit;
	}
}
