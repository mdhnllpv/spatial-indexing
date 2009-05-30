package query;

import java.util.Map;
import java.util.Set;

import scoring.DocumentScorrer;
import tokenizer.DocumentUnit;
import tokenizer.TokenizerImpl;

public class QueryProcessor {
	private TokenizerImpl toknizer;

	public QueryProcessor(TokenizerImpl tokenizer) {
		this.toknizer = tokenizer;
	}

	public DocumentUnit answer(Set<String> query) {
		double minScore = 0;
		DocumentUnit paragraph = null;
		
		Map<String, Double> queryVector = this.toknizer.assignTfIdf(query);
		for (DocumentUnit unit : this.toknizer.getDocumentUnits()) {
			double score = DocumentScorrer.cosine(queryVector, unit.getTerms());
			if (score > minScore) {
				minScore = score;
				paragraph = unit;
			}

		}
		return paragraph;
	}
}
