package separator;

public class DocumentUnitSeparatorFactory {
	public static IDocumentUnitSeparator createDocumentUnitSeparator(String input) {
		return new SingleCharacterSeparator('\n');
	}
}
