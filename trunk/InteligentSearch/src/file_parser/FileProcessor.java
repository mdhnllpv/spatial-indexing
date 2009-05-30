package file_parser;

import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
	private static Map<String, IFileParser> supportedFiles;
	static {
		supportedFiles = new HashMap<String, IFileParser>();
		supportedFiles.put(".txt", new TXTParserImpl());
		supportedFiles.put(".doc", new WORDParserImpl());
	}

	public static String process(String fileName) {
		int dotIndex = 0;
		for (int i = 0; i < fileName.length(); i++) {
			if (fileName.charAt(i) == '.') {
				dotIndex = i;
			}
		}
		String extension = fileName.substring(dotIndex).toLowerCase();

		if (supportedFiles.containsKey(extension)) {
			return supportedFiles.get(extension).parseFile(fileName);
		} else {
			throw new IllegalArgumentException("unsuported file format");
		}

	}

}
