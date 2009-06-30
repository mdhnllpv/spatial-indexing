package separator;

public class DocumentUnitSeparatorFactory {
	public static IDocumentUnitSeparator createDocumentUnitSeparator(
			String input) {
		int newLinesCnt = 0;
		int doubleNewLinesCnt = 0;
		final int sz = input.length();
		boolean isLastSingleSeparator = false;
		boolean isLastDoubleSeparator = false;
		int longestLine = 0;
		int lastNewLineIndex = 0;
		final int threshold = 500;
		for (int i = 0; i < sz / 2; i++) {
			if (input.charAt(i) == '\n') {
				longestLine = Math.max(longestLine, i - lastNewLineIndex);
				lastNewLineIndex = i;
				if (isLastSingleSeparator == false) {
					isLastSingleSeparator = true;
					newLinesCnt++;
				} else {
					isLastSingleSeparator = false;
				}

				if (isLastDoubleSeparator == false
						&& input.charAt(i + 1) == '\n') {
					isLastDoubleSeparator = true;
					doubleNewLinesCnt++;
				} else {
					isLastDoubleSeparator = false;
				}
				
			}
		}
		if (longestLine < threshold) {
			return new ShortestLineSeparator();
		}
		if (newLinesCnt >= doubleNewLinesCnt) {
			return new SingleCharacterSeparator('\n');
		} else {
			return new DoubleNewLinesSeparator();
		}
	}
}
