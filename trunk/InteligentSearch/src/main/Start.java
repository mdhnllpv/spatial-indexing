package main;

import java.io.IOException;

import tokenizer.TokenizerImpl;

public class Start {
	
	public static void main(String[] args) throws IOException {
//		PDFParserImpl parser = new PDFParserImpl();
//		String s = parser.parseFile("test.pdf");
		String s = 	"	This is first line of paragrah1\n this is second line of paragraph1"+
					"	This is first line of paragrah2\n this is second line of paragraph2"+
					"	This is first line of paragrah3\n this is second line of paragraph3";
		TokenizerImpl tokenizer = new TokenizerImpl();
		tokenizer.tokenize(s);
		System.out.println(tokenizer.getDocumentFrequency());
		System.out.println(tokenizer.getTermFrequency());
		System.out.println(tokenizer.getDocumentUnits());
	}
}