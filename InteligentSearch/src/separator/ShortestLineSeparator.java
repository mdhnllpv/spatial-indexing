package separator;

import java.util.HashSet;
import java.util.Set;

public class ShortestLineSeparator implements IDocumentUnitSeparator {
	private Set<Integer> separators = null;

	@Override
	public boolean isSeparator(int index, String text) {
		if (separators == null) {
			buildSeparators(text);
		}
		return separators.contains(index);
	}

	private void buildSeparators(String text) {
		separators = new HashSet<Integer>();
		int sz = text.length();
		int lastNewLine = 0;
		int rowLength = 0;
		float threshold = 0.7f;
		for (int i = 0; i < sz; i++) {
			if (text.charAt(i) == '\n') {
				if (rowLength < i - lastNewLine) {
					rowLength = i - lastNewLine;
				} else {
					if ( ((float)(i - lastNewLine) / rowLength) < threshold ) {
						separators.add(i-1);
					}
				}
				lastNewLine = i;
			}
		}
	}

}
