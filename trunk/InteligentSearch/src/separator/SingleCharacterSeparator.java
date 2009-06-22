package separator;

public class SingleCharacterSeparator implements IDocumentUnitSeparator {
	
	private char separator;
	
	public SingleCharacterSeparator(char ch) {
		separator = ch;
	}

	@Override
	public boolean isSeparator(int index, String text) {
		return text.charAt(index) == separator;
	}

}
