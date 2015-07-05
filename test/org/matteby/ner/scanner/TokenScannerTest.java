package org.matteby.ner.scanner;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class TokenScannerTest {
	
	// helper method to assert the number of tokens that TokenScanner finds
	public void assertTokenCount(String text, int tokenCount) {

		TokenScanner scanner = new TokenScanner(new StringReader(text));
		int count = 0;
		while(scanner.hasNext()) {
			scanner.next();
			count++;
		}
		assertTrue(count == tokenCount);
	}
	
	@Test
	public void testTokenScanner() {
		assertTokenCount("A dog ran.", 4);
		assertTokenCount("The cost is $7.9 billion.", 6);
		assertTokenCount("The cost is $7.90.", 5);
	}

}
