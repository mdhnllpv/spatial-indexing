package separator;

public class DoubleNewLinesSeparator implements IDocumentUnitSeparator {

	@Override
	public boolean isSeparator(int index, String text) {
		text.length();
		if (text.charAt(index) == '\n' && text.charAt(index + 1) == '\n') {
			return true;
		}
		return false;
	}

}
