package file_parser;

public interface IFileParser {
	/**
	 * Parse file
	 * 
	 * @param fileName
	 *            file name
	 * @return String representation of the file
	 */
	public String parseFile(String fileName);
}
