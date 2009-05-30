package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import tokenizer.TokenizerImpl;
import file_parser.IFileParser;
import file_parser.TXTParserImpl;

public class Start {
	
	public static void main(String[] args) throws IOException {
//		PDFParserImpl parser = new PDFParserImpl();
//		String s = parser.parseFile("test.pdf");
		IFileParser parser = new TXTParserImpl(); 
		String s = 	parser.parseFile("test.txt");
		TokenizerImpl tokenizer = new TokenizerImpl();
		tokenizer.tokenize(s);
		tokenizer.assignTfIdf();
		System.out.println(tokenizer.getDocumentUnits());
		Set<String> query = new HashSet<String>();
		query.add("whenever");
		query.add("have");
		query.add("certainly");
		System.out.println(tokenizer.assignTfIdf(new HashSet<String>()));
		System.out.println(tokenizer.assignTfIdf(query));
		//System.out.println(tokenizer.getDocumentFrequency());
		//System.out.println(tokenizer.getTermFrequency());
		//System.out.println(tokenizer.getDocumentUnits());
		//System.out.println(tokenizer.getDocumentUnits().size());
	}
}