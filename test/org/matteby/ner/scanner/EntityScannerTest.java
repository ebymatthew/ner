package org.matteby.ner.scanner;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.matteby.ner.model.Entity;
import org.matteby.ner.model.ISentencePart;

public class EntityScannerTest {
	
	// helper method to assert the number of entities that EntityScanner finds
	public void assertEntityCount(Iterator<String> tokens, int entityCount) {

		EntityScanner scanner = new EntityScanner(tokens);
		int count = 0;
		while(scanner.hasNext()) {
			ISentencePart sp = scanner.next();
			if(sp instanceof Entity) {
				count++;
			}
		}
		assertTrue(count == entityCount);
	}
	
	@Test
	public void testEntityScanner() {
		// test that entity scanner finds the entity
		List<String> tokens = new ArrayList<String>();
		tokens.add("Broyden-Fletcher-Goldfarb-Shanno");
		tokens.add("test");
		assertEntityCount(tokens.iterator(), 1);
		
		// test that entity scanner finds the entity when entering the pipeline as UTF-8
		String testSentence = "Broyden\u2013Fletcher\u2013Goldfarb\u2013Shanno is a name.";
		SentenceScanner sentenceScanner = new SentenceScanner(new StringReader(testSentence));
		// keep processing as long as there are tokens
		while ((sentenceScanner.hasNext())) {
			// get the next sentence
			String sentenceInput = sentenceScanner.next();
			TokenScanner tokenScanner =  new TokenScanner(new StringReader(sentenceInput));
			assertEntityCount(tokenScanner, 1);
		}
		
		
			
	}

}
