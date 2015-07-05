package org.matteby.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.matteby.ner.model.Document;
import org.matteby.ner.model.ISentencePart;
import org.matteby.ner.model.Sentence;
import org.matteby.ner.scanner.EntityScanner;
import org.matteby.ner.scanner.SentenceScanner;
import org.matteby.ner.scanner.TokenScanner;

/**
 * DocumentProcessor takes an input text file and processes into a Document data
 * structure
 */
public class DocumentProcessor {

	/*
	 * Process the input file and return a Document
	 */
	public Document processDocument(File input) throws FileNotFoundException, IOException {
		// return value
		Document doc = new Document();

		// wrap the file in a FileReader to stream input
		// FileReader uses platform default charset
		// ASSUMPTION: platform default charset is appropriate for input text
		// wrap the FileReader in a try statement to auto close
		try (FileReader reader = new FileReader(input)) {
			// buffer the input stream for efficient disk access
			BufferedReader bufferedReader = new BufferedReader(reader);
			// create a sentence scanner to iterate over sentences in document
			SentenceScanner sentenceScanner = new SentenceScanner(bufferedReader);

			// keep processing as long as there are tokens
			while ((sentenceScanner.hasNext())) {
				// get the next sentence
				String sentenceInput = sentenceScanner.next();
				Sentence sentence = buildSentence(sentenceInput);
				doc.appendSentence(sentence);
			}
		}

		return doc;
	}

	/**
	 * process the input string and return a Sentence
	 */
	private Sentence buildSentence(String input) {
		Sentence sentence = new Sentence();

		StringReader reader = new StringReader(input);

		// create a TokenScanner to iterate over tokens in the input text
		TokenScanner tokenScanner = new TokenScanner(reader);
		// create an EntityScanner to scan over Entities and Tokens in the token stream
		EntityScanner entityScanner = new EntityScanner(tokenScanner);
		
		while ((entityScanner.hasNext())) {
			// get the next sentence part
			ISentencePart sentencePart = entityScanner.next();
			sentence.appendSentencePart(sentencePart);
		}

		return sentence;
	}
}
