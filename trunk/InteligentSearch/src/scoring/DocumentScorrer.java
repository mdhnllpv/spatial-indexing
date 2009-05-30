package scoring;

import java.util.Map;
import java.util.Set;

public class DocumentScorrer {

	public static double Scoring(Map<String, Double> unitVector,
			Set<String> query) {
		double score = 0;

		for (String key : query) {
			if (unitVector.containsKey(key)) {
				score += unitVector.get(key);
			}
		}

		return score;
	}

	public static double dotProduct(Map<String, Double> unit1,
			Map<String, Double> unit2) {
		double dotProduct = 0;
		for (String key : unit1.keySet()) {
			if (unit2.containsKey(key)) {
				dotProduct += unit1.get(key) * unit2.get(key);
			}
		}

		return dotProduct;
	}

	public static double length(Map<String, Double> unit) {
		double len = 0;

		for (String key : unit.keySet()) {
			double c = unit.get(key);
			len += c * c;
		}

		return Math.sqrt(len);
	}

	public static double cosine(Map<String, Double> unit1,
			Map<String, Double> unit2) {
		return dotProduct(unit1, unit2) / (length(unit1) * length(unit2));

	}
}
