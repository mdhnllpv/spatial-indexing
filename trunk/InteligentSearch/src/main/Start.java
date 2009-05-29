package main;

import java.io.IOException;

import file_parser.PDFParserImpl;

public class Start {
	
	public static void main(String[] args) throws IOException {
		PDFParserImpl parser = new PDFParserImpl();
		System.out.println(parser.parseFile("test.pdf"));
	}
}