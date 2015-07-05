package org.matteby.ner.scanner;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class SentenceScannerTest {
	
	// helper method to assert the number of sentences that SentenceScanner finds
	public void assertSentenceCount(String text, int sentenceCount) {

		SentenceScanner scanner = new SentenceScanner(new StringReader(text));
		int count = 0;
		while(scanner.hasNext()) {
			scanner.next();
			count++;
		}
		assertTrue(count == sentenceCount);
	}
	
	@Test
	public void testSentenceScanner() {
		assertSentenceCount("The cost is $7.1. My company Inc. is great.", 2);
		assertSentenceCount("The cost is $7.1. \"My company Inc. is great.\"", 2);
		assertSentenceCount("The cost is \"$7.1!\" Test sentence. \"My company Inc. is great.\"", 3);
	}
	
}
