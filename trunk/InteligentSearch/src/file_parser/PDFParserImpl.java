package file_parser;

import java.io.File;
import java.io.FileInputStream;

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public class PDFParserImpl implements IFileParser {

	private PDFParser parser;
	private String parsedText;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;

	@Override
	public String parseFile(String fileName) {

		try {
			parser = new PDFParser(new FileInputStream(new File(fileName)));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			System.out.println(e.getMessage());
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}

		return parsedText;
	}

}
