package file_parser;

import java.io.IOException;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public class PDFParserImpl implements IFileParser {

	@Override
	public String parseFile(String fileName) {
		String res = null;
		try {
			PDDocument document = PDDocument.load(fileName);
			PDFTextStripper stripper = new PDFTextStripper();
			res = stripper.getText(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

}
