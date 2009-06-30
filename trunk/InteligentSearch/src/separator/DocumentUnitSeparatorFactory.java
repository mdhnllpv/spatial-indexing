package separator;

import java.util.Set;

public class DocumentUnitSeparatorFactory {
	public static IDocumentUnitSeparator createDocumentUnitSeparator(
			String input) {
		int singleSeparator = 0;
		int doubleSeparator = 0;
		int sz = input.length() / 2;
		boolean isLastSingleSeparator = false;
		boolean isLastDoubleSeparator = false;
		for (int i = 0; i < sz; i++) {
			if (input.charAt(i) == '\n'){
				if ( isLastSingleSeparator == false) {
					isLastSingleSeparator = true;
					singleSeparator ++;
				}
				else {
					isLastSingleSeparator = false;
				}
				
				if ( isLastDoubleSeparator == false && input.charAt(i+1) == '\n') {
					isLastDoubleSeparator = true;
					doubleSeparator ++;
				}
				else {
					isLastDoubleSeparator = false;
				}
			}
		}
		System.out.println(singleSeparator);
		System.out.println(doubleSeparator);
		if ( singleSeparator >= doubleSeparator ) {
			return new SingleCharacterSeparator('\n');
		} else {
			return new DoubleNewLinesSeparator();
		}
	}
}
