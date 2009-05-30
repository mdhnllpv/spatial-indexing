package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import tokenizer.TokenizerImpl;
import file_parser.FileProcessor;

public class Start {
	
	public static void main(String[] args) throws IOException {
		String s = 	FileProcessor.process("test.TXT");
		TokenizerImpl tokenizer = new TokenizerImpl();
		tokenizer.tokenize(s);
		tokenizer.assignTfIdf();
		Set<String> query = new HashSet<String>();
		query.add("whenever");
		query.add("have");
		query.add("certainly");
		System.out.println(tokenizer.assignTfIdf(query));
	}
}