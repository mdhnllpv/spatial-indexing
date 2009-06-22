package engine;

import java.util.Map;

public class DocumentUnit {
	private Map<String, Double> terms;

	private int srart;

	private int end;

	public DocumentUnit(Map<String, Double> terms, int start, int end) {
		this.srart = start;
		this.end = end;
		this.terms = terms;
	}

	public Map<String, Double> getTerms() {
		return terms;
	}

	public int getSrart() {
		return srart;
	}

	public int getEnd() {
		return end;
	}

	public void setSrart(int srart) {
		this.srart = srart;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
