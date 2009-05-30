package main;

import java.util.HashSet;
import java.util.Set;

import query.QueryProcessor;
import tokenizer.DocumentUnit;
import tokenizer.TokenizerImpl;
import file_parser.FileProcessor;

public class Start {
	
	public static void main(String[] args){
		String s = 	FileProcessor.process("test.TXT");
		TokenizerImpl tokenizer = new TokenizerImpl();
		tokenizer.tokenize(s);
		tokenizer.assignTfIdf();
		Set<String> query = new HashSet<String>();
		query.add("barricade");
		QueryProcessor queryProcessor = new QueryProcessor(tokenizer);
		DocumentUnit unit = queryProcessor.answer(query);
		System.out.println(s.substring(unit.getSrart(), unit.getEnd()));
	}
}