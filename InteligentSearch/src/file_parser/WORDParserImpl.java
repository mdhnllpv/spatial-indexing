package file_parser;

import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WORDParserImpl implements IFileParser {

	@Override
	public String parseFile(String fileName) {
		POIFSFileSystem filesystem = null;
		try{
			filesystem = new POIFSFileSystem(new FileInputStream(fileName));
			HWPFDocument document = new HWPFDocument(filesystem);
			WordExtractor extractor = new WordExtractor(document);
			return extractor.getText();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
